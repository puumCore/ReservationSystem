package org.puumCore._odysseySafaris._custom;

import javafx.application.Platform;
import org.puumCore._odysseySafaris.Main;
import org.puumCore._odysseySafaris._models._object.*;
import org.puumCore._odysseySafaris._models._table.LogsInfo;
import org.puumCore._odysseySafaris._models._table.Reservations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.*;

/**
 * @author Puum Core (Mandela Muriithi)<br>
 * <a href = https://github.com/puumCore>GitHub: Mandela Muriithi</a>
 * @version 1.0
 * @since 18/03/2022
 */

public abstract class Brain extends Assistant {

    protected final String ALL_WILDCARD = "#all";

    protected final List<LogsInfo> get_logs_based_on_param(String param) {
        if (Main.DATA_SOURCE_CONNECTION == null) {
            return null;
        }
        List<LogsInfo> logsInfoList = new ArrayList<>();
        final String rootQuery = "select\n" +
                "\tl.`timestamp` ,\n" +
                "\tl.voucherId ,\n" +
                "\tl.info\n" +
                "from\n" +
                "\tlogs l";
        try {
            PreparedStatement preparedStatement = Main.DATA_SOURCE_CONNECTION.prepareStatement((param == null) ? rootQuery.concat(";") : rootQuery.concat("\n" +
                    "where\n" +
                    "\t(l.info like ?);"));
            if (param != null) {
                preparedStatement.setString(1, "%" + param + "%");
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    logsInfoList.add(new LogsInfo(resultSet.getString(1), resultSet.getInt(2), resultSet.getString(3)));
                }
            }
            resultSet.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
            new Thread(write_stack_trace(e)).start();
            Platform.runLater(() -> programmer_error(e).show());
        }
        return logsInfoList;
    }

    protected final Set<String> get_log_search_suggestions() {
        if (Main.DATA_SOURCE_CONNECTION == null) {
            return null;
        }
        Set<String> stringSet = new HashSet<>();
        try {
            PreparedStatement preparedStatement = Main.DATA_SOURCE_CONNECTION.prepareStatement("select\n" +
                    "\tl.info\n" +
                    "from\n" +
                    "\tlogs l;");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    String[] split = resultSet.getString(1).split(" ");
                    stringSet.addAll(Arrays.asList(split));
                }
            }
            resultSet.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
            new Thread(write_stack_trace(e)).start();
            Platform.runLater(() -> programmer_error(e).show());
        }
        return stringSet;
    }

    protected final Boolean update_voucher_confirmation(Integer voucherId, Person confirmPerson) {
        if (Main.DATA_SOURCE_CONNECTION == null) {
            return null;
        }
        boolean isOkay = false;
        try {
            Savepoint savepoint = Main.DATA_SOURCE_CONNECTION.setSavepoint();
            PreparedStatement preparedStatement = Main.DATA_SOURCE_CONNECTION.prepareStatement("insert\n" +
                    "\tinto\n" +
                    "\tconfirmation (name,\n" +
                    "\tphone)\n" +
                    "where\n" +
                    "values (?,\n" +
                    "?);", PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, confirmPerson.getName());
            preparedStatement.setString(2, confirmPerson.getPhone());
            int count = preparedStatement.executeUpdate();
            if (count == 1) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys != null && generatedKeys.next()) {
                    confirmPerson.setId(generatedKeys.getInt(1));
                    preparedStatement = Main.DATA_SOURCE_CONNECTION.prepareStatement("update\n" +
                            "\tvoucher\n" +
                            "set\n" +
                            "\tconfirmId = ?\n" +
                            "where\n" +
                            "\tvoucherId = ?;");
                    preparedStatement.setInt(1, confirmPerson.getId());
                    preparedStatement.setInt(2, voucherId);
                    isOkay = (preparedStatement.executeUpdate() == 1);
                    if (!isOkay) {
                        Main.DATA_SOURCE_CONNECTION.rollback(savepoint);
                    }
                }
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            new Thread(write_stack_trace(e)).start();
            Platform.runLater(() -> programmer_error(e).show());
        }
        return isOkay;
    }

    protected final Boolean update_voucher(Voucher voucher) {
        if (Main.DATA_SOURCE_CONNECTION == null) {
            return null;
        }
        boolean isOkay = false;
        try {
            PreparedStatement preparedStatement;
            if (voucher.getHotel() != null) {
                preparedStatement = Main.DATA_SOURCE_CONNECTION.prepareStatement("update\n" +
                        "\thotel\n" +
                        "set\n" +
                        "\tbranch = coalesce (?,\n" +
                        "\tbranch)\n" +
                        "where\n" +
                        "\thotelId = ?;");
                preparedStatement.setString(1, voucher.getHotel().getBranch());
                preparedStatement.setInt(2, voucher.getHotel().getId());
                isOkay = (preparedStatement.executeUpdate() == 1);
                if (!isOkay) {
                    preparedStatement.close();
                    create_log(voucher.getId(), new Log("Hotel update", "Could not update hotel info"));
                    return false;
                }
                create_log(voucher.getId(), new Log("Hotel update", "Updated hotel info"));
            }
            if (voucher.getHeadCount() != null) {
                preparedStatement = Main.DATA_SOURCE_CONNECTION.prepareStatement("update\n" +
                        "\thead_count\n" +
                        "set\n" +
                        "\tadults = coalesce(?, adults),\n" +
                        "\tchildren = coalesce(?, children),\n" +
                        "\tinfants = coalesce(?, infants),\n" +
                        "\treservations = coalesce(?, reservations)\n" +
                        "\twhere countId = ?;");
                preparedStatement.setInt(1, voucher.getHeadCount().getAdults());
                preparedStatement.setInt(2, voucher.getHeadCount().getChildren());
                preparedStatement.setInt(3, voucher.getHeadCount().getInfants());
                preparedStatement.setInt(4, voucher.getHeadCount().getRes());
                preparedStatement.setInt(5, voucher.getHeadCount().getId());
                isOkay = (preparedStatement.executeUpdate() == 1);
                if (!isOkay) {
                    preparedStatement.close();
                    create_log(voucher.getId(), new Log("Head count update", "Could not update head count info"));
                    return false;
                }
                create_log(voucher.getId(), new Log("Head count update", "Updated head count info"));
            }
            if (voucher.getTimeLine() != null) {
                preparedStatement = Main.DATA_SOURCE_CONNECTION.prepareStatement("update\n" +
                        "\ttime_line\n" +
                        "set\n" +
                        "\tarrival = coalesce (?,\n" +
                        "\tarrival),\n" +
                        "\tdeparture = coalesce (?,\n" +
                        "\tdeparture),\n" +
                        "\tdays = coalesce (?,\n" +
                        "\tdays),\n" +
                        "\tnights = coalesce (?,\n" +
                        "\tnights)\n" +
                        "where\n" +
                        "\tunitId = ?;");
                preparedStatement.setString(1, voucher.getTimeLine().getArrival());
                preparedStatement.setString(2, voucher.getTimeLine().getDeparture());
                preparedStatement.setInt(3, voucher.getTimeLine().getDays());
                preparedStatement.setInt(4, voucher.getTimeLine().getNights());
                preparedStatement.setInt(5, voucher.getTimeLine().getId());
                isOkay = (preparedStatement.executeUpdate() == 1);
                if (!isOkay) {
                    preparedStatement.close();
                    create_log(voucher.getId(), new Log("Time line update", "Could not update time line info"));
                    return false;
                }
                create_log(voucher.getId(), new Log("Time line update", "Updated time line info"));
            }
            if (voucher.getRoomType() != null) {
                preparedStatement = Main.DATA_SOURCE_CONNECTION.prepareStatement("update\n" +
                        "\troom_type\n" +
                        "set\n" +
                        "\tsingles = coalesce (?,\n" +
                        "\tsingles),\n" +
                        "\tdoubles = coalesce (?,\n" +
                        "\tdoubles),\n" +
                        "\ttripplets = coalesce (?,\n" +
                        "\ttripplets)\n" +
                        "where\n" +
                        "\troomId = ?;");
                preparedStatement.setBoolean(1, voucher.getRoomType().getSingles());
                preparedStatement.setBoolean(2, voucher.getRoomType().getDoubles());
                preparedStatement.setBoolean(3, voucher.getRoomType().getTriples());
                preparedStatement.setInt(5, voucher.getRoomType().getId());
                isOkay = (preparedStatement.executeUpdate() == 1);
                if (!isOkay) {
                    preparedStatement.close();
                    return false;
                }
            }
            preparedStatement = Main.DATA_SOURCE_CONNECTION.prepareStatement("update\n" +
                    "\tvoucher\n" +
                    "set\n" +
                    "\tSTATUS = coalesce (?,\n" +
                    "\tSTATUS),\n" +
                    "\tremarks = coalesce (?,\n" +
                    "\tremarks),\n" +
                    "\tpaid_by = coalesce (?,\n" +
                    "\tpaid_by)\n" +
                    "where\n" +
                    "\tvoucherId = ?;");
            preparedStatement.setString(1, voucher.getStatus());
            preparedStatement.setString(2, voucher.getRemarks());
            preparedStatement.setString(3, voucher.getPaidBy());
            preparedStatement.setInt(5, voucher.getId());
            isOkay = (preparedStatement.executeUpdate() == 1);
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            new Thread(write_stack_trace(e)).start();
            Platform.runLater(() -> programmer_error(e).show());
        }
        return isOkay;
    }

    protected final Boolean delete_voucher_data(Voucher voucher) {
        if (Main.DATA_SOURCE_CONNECTION == null) {
            return null;
        }
        boolean isOkay = false;
        try {
            PreparedStatement preparedStatement = Main.DATA_SOURCE_CONNECTION.prepareStatement("delete from hotel where hotelId = ?;");
            preparedStatement.setInt(1, voucher.getHotel().getId());
            preparedStatement.executeUpdate();
            preparedStatement.clearParameters();

            preparedStatement = Main.DATA_SOURCE_CONNECTION.prepareStatement("delete from client where clientId = ?;");
            preparedStatement.setInt(1, voucher.getClient().getId());
            preparedStatement.executeUpdate();
            preparedStatement.clearParameters();

            preparedStatement = Main.DATA_SOURCE_CONNECTION.prepareStatement("delete from head_count where countId = ?;");
            preparedStatement.setInt(1, voucher.getHeadCount().getId());
            preparedStatement.executeUpdate();
            preparedStatement.clearParameters();

            preparedStatement = Main.DATA_SOURCE_CONNECTION.prepareStatement("delete from room_type where roomId = ?;");
            preparedStatement.setInt(1, voucher.getRoomType().getId());
            preparedStatement.executeUpdate();
            preparedStatement.clearParameters();

            preparedStatement = Main.DATA_SOURCE_CONNECTION.prepareStatement("delete from time_line where unitId = ?;");
            preparedStatement.setInt(1, voucher.getTimeLine().getId());
            preparedStatement.executeUpdate();
            preparedStatement.clearParameters();

            preparedStatement = Main.DATA_SOURCE_CONNECTION.prepareStatement("delete from meal_plan where planId = ?;");
            preparedStatement.setInt(1, voucher.getMealPlan().getId());
            preparedStatement.executeUpdate();
            preparedStatement.clearParameters();

            if (voucher.getConfirmPerson() != null) {
                preparedStatement = Main.DATA_SOURCE_CONNECTION.prepareStatement("delete from confirmation where confirmId = ?;");
                preparedStatement.setInt(1, voucher.getConfirmPerson().getId());
                preparedStatement.executeUpdate();
                preparedStatement.clearParameters();
            }

            preparedStatement = Main.DATA_SOURCE_CONNECTION.prepareStatement("delete from voucher where voucherId = ?;");
            preparedStatement.setInt(1, voucher.getId());
            preparedStatement.executeUpdate();
            preparedStatement.clearParameters();

            preparedStatement.close();
            isOkay = true;
        } catch (SQLException e) {
            e.printStackTrace();
            new Thread(write_stack_trace(e)).start();
            Platform.runLater(() -> programmer_error(e).show());
        }
        return isOkay;
    }


    protected final Voucher get_full_voucher_with_its_ID(Integer voucherID) {
        if (Main.DATA_SOURCE_CONNECTION == null) {
            return null;
        }
        Voucher voucher = new Voucher();
        try {
            PreparedStatement preparedStatement = Main.DATA_SOURCE_CONNECTION.prepareStatement("select\n" +
                    "\t*\n" +
                    "from\n" +
                    "\tvoucher_plus vp\n" +
                    "where\n" +
                    "\tvp.voucherId = ?;");
            preparedStatement.setInt(1, voucherID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                voucher.setId(resultSet.getInt(1));

                Hotel hotel = new Hotel();
                hotel.setId(resultSet.getInt(2));
                hotel.setName(resultSet.getString(3));
                hotel.setBranch(resultSet.getString(4));
                voucher.setHotel(hotel);

                Person person = new Person();
                person.setId(resultSet.getInt(5));
                person.setName(resultSet.getString(6));
                person.setPhone(resultSet.getString(7));
                voucher.setClient(person);

                HeadCount headCount = new HeadCount();
                headCount.setAdults(resultSet.getInt(9));
                headCount.setChildren(resultSet.getInt(10));
                headCount.setInfants(resultSet.getInt(11));
                headCount.setRes(resultSet.getInt(12));
                voucher.setHeadCount(headCount);

                RoomType roomType = new RoomType();
                roomType.setId(resultSet.getInt(13));
                roomType.setSingles(resultSet.getBoolean(14));
                roomType.setDoubles(resultSet.getBoolean(15));
                roomType.setTriples(resultSet.getBoolean(16));
                voucher.setRoomType(roomType);

                TimeLine timeLine = new TimeLine();
                timeLine.setId(resultSet.getInt(17));
                timeLine.setArrival(resultSet.getString(18));
                timeLine.setDeparture(resultSet.getString(19));
                timeLine.setDays(resultSet.getInt(20));
                timeLine.setNights(resultSet.getInt(21));
                voucher.setTimeLine(timeLine);

                MealPlan mealPlan = new MealPlan();
                mealPlan.setId(resultSet.getInt(22));
                mealPlan.setB_b(resultSet.getBoolean(23));
                mealPlan.setH_b(resultSet.getBoolean(24));
                mealPlan.setF_b(resultSet.getBoolean(25));
                mealPlan.setLunch(resultSet.getBoolean(26));
                mealPlan.setDinner(resultSet.getBoolean(27));
                mealPlan.setXtra_direct(resultSet.getBoolean(28));
                voucher.setMealPlan(mealPlan);

                voucher.setRemarks(resultSet.getString(29));
                voucher.setPaidBy(resultSet.getString(30));

                Person person1 = new Person();
                person1.setId(resultSet.getInt(31));
                person1.setName(resultSet.getString(32));
                person1.setPhone(resultSet.getString(33));
                voucher.setConfirmPerson(person1);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
            new Thread(write_stack_trace(e)).start();
            Platform.runLater(() -> programmer_error(e).show());
        }
        return voucher;
    }

    protected final List<Reservations> get_reservations_based_on_param(String param) {
        if (Main.DATA_SOURCE_CONNECTION == null) {
            return null;
        }
        List<Reservations> reservationsList = new ArrayList<>();
        final String rootQuery = "select\n" +
                "\t*\n" +
                "from\n" +
                "\treservations";
        try {
            PreparedStatement preparedStatement = Main.DATA_SOURCE_CONNECTION.prepareStatement((param == null) ? rootQuery.concat(";") : rootQuery.concat(" r\n" +
                    "where\n" +
                    "\t(r.hotel_name like ?)\n" +
                    "\tor (r.hotel_branch like ?)\n" +
                    "\tor (r.client_name like ?)\n" +
                    "\tor (r.client_phone like ?)\n" +
                    "\tor (r.arrival like ?)\n" +
                    "\tor (r.departure like ?)\n" +
                    "\tor (r.remarks like ?);"));
            if (param != null) {
                for (int index = 1; index <= 9; ++index) {
                    preparedStatement.setString(index, "%" + param + "%");
                }
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    reservationsList.add(new Reservations(resultSet.getInt(1),
                            resultSet.getString(4),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getInt(7),
                            resultSet.getInt(8),
                            resultSet.getInt(9),
                            resultSet.getInt(10),
                            resultSet.getInt(11),
                            (resultSet.getBoolean(12) ? "True" : "False"),
                            (resultSet.getBoolean(13) ? "True" : "False"),
                            (resultSet.getBoolean(14) ? "True" : "False"),
                            resultSet.getString(15),
                            resultSet.getString(16),
                            resultSet.getInt(17),
                            resultSet.getInt(18),
                            get_dynamic_alert("Remarks", resultSet.getString(25)),
                            resultSet.getString(26)
                    ));
                }
            }
            resultSet.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
            new Thread(write_stack_trace(e)).start();
            Platform.runLater(() -> programmer_error(e).show());
        }
        return reservationsList;
    }

    protected final Set<String> get_voucher_search_suggestions() {
        if (Main.DATA_SOURCE_CONNECTION == null) {
            return null;
        }
        Set<String> stringSet = new HashSet<>();
        try {
            PreparedStatement preparedStatement = Main.DATA_SOURCE_CONNECTION.prepareStatement("select\n" +
                    "\tdistinct r.hotel_name,\n" +
                    "\tr.hotel_branch ,\n" +
                    "\tr.status ,\n" +
                    "\tr.client_name ,\n" +
                    "\tr.client_phone ,\n" +
                    "\tr.remarks,\n" +
                    "\tr.paid_by\n" +
                    "from\n" +
                    "\treservations r;");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    for (String result : new String[]{resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7)}) {
                        if (result != null) {
                            stringSet.add(result);
                        }
                    }
                }
            }
            resultSet.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
            new Thread(write_stack_trace(e)).start();
            Platform.runLater(() -> programmer_error(e).show());
        }
        return stringSet;
    }

    protected final Boolean create_voucher(Voucher voucher) {
        if (Main.DATA_SOURCE_CONNECTION == null) {
            return null;
        }
        boolean isOkay = false;
        create_log(voucher.getId(), new Log("Creation Attempt", "Trying to create a new voucher"));
        try {
            Savepoint savepoint = Main.DATA_SOURCE_CONNECTION.setSavepoint();
            //Adding new hotel
            PreparedStatement preparedStatement = Main.DATA_SOURCE_CONNECTION.prepareStatement("insert\n" +
                    "\tinto\n" +
                    "\thotel (name,\n" +
                    "\tbranch)\n" +
                    "values (?,\n" +
                    "?);", PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, voucher.getHotel().getName());
            preparedStatement.setString(2, voucher.getHotel().getBranch());
            int count = preparedStatement.executeUpdate();
            if (count == 1) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys != null && generatedKeys.next()) {
                    voucher.getHotel().setId(generatedKeys.getInt(1));
                    preparedStatement.clearParameters();
                    //Adding new client
                    preparedStatement = Main.DATA_SOURCE_CONNECTION.prepareStatement("insert\n" +
                            "\tinto\n" +
                            "\tclient (name,\n" +
                            "\tphone)\n" +
                            "values (?,\n" +
                            "?);", PreparedStatement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, voucher.getClient().getName());
                    preparedStatement.setString(2, voucher.getClient().getPhone());
                    count = preparedStatement.executeUpdate();
                    if (count == 1) {
                        generatedKeys = preparedStatement.getGeneratedKeys();
                        if (generatedKeys != null && generatedKeys.next()) {
                            voucher.getClient().setId(generatedKeys.getInt(1));
                            preparedStatement.clearParameters();
                            //Adding new head count
                            preparedStatement = Main.DATA_SOURCE_CONNECTION.prepareStatement("insert\n" +
                                    "\tinto\n" +
                                    "\thead_count (adults,\n" +
                                    "\tchildren,\n" +
                                    "\tinfants,\n" +
                                    "\treservations)\n" +
                                    "values (?,\n" +
                                    "?,\n" +
                                    "?,\n" +
                                    "?);", PreparedStatement.RETURN_GENERATED_KEYS);
                            preparedStatement.setInt(1, voucher.getHeadCount().getAdults());
                            preparedStatement.setInt(2, voucher.getHeadCount().getChildren());
                            preparedStatement.setInt(3, voucher.getHeadCount().getInfants());
                            preparedStatement.setInt(4, voucher.getHeadCount().getRes());
                            count = preparedStatement.executeUpdate();
                            if (count == 1) {
                                generatedKeys = preparedStatement.getGeneratedKeys();
                                if (generatedKeys != null && generatedKeys.next()) {
                                    voucher.getHeadCount().setId(generatedKeys.getInt(1));
                                    preparedStatement.clearParameters();
                                    //Adding new room type
                                    preparedStatement = Main.DATA_SOURCE_CONNECTION.prepareStatement("insert\n" +
                                            "\tinto\n" +
                                            "\troom_type (singles,\n" +
                                            "\tdoubles,\n" +
                                            "\ttripplets)\n" +
                                            "values (?,\n" +
                                            "?,\n" +
                                            "?);", PreparedStatement.RETURN_GENERATED_KEYS);
                                    preparedStatement.setBoolean(1, voucher.getRoomType().getSingles() != null && voucher.getRoomType().getSingles());
                                    preparedStatement.setBoolean(2, voucher.getRoomType().getDoubles() != null && voucher.getRoomType().getDoubles());
                                    preparedStatement.setBoolean(3, voucher.getRoomType().getTriples() != null && voucher.getRoomType().getTriples());
                                    count = preparedStatement.executeUpdate();
                                    if (count == 1) {
                                        generatedKeys = preparedStatement.getGeneratedKeys();
                                        if (generatedKeys != null && generatedKeys.next()) {
                                            voucher.getRoomType().setId(generatedKeys.getInt(1));
                                            preparedStatement.clearParameters();
                                            //Adding timeline
                                            preparedStatement = Main.DATA_SOURCE_CONNECTION.prepareStatement("insert\n" +
                                                    "\tinto\n" +
                                                    "\ttime_line (arrival,\n" +
                                                    "\tdeparture,\n" +
                                                    "\tdays,\n" +
                                                    "\tnights)\n" +
                                                    "values (?,\n" +
                                                    "?,\n" +
                                                    "?,\n" +
                                                    "?);", PreparedStatement.RETURN_GENERATED_KEYS);
                                            preparedStatement.setString(1, voucher.getTimeLine().getArrival());
                                            preparedStatement.setString(2, voucher.getTimeLine().getDeparture());
                                            preparedStatement.setInt(3, voucher.getTimeLine().getDays());
                                            preparedStatement.setInt(4, voucher.getTimeLine().getNights());
                                            count = preparedStatement.executeUpdate();
                                            if (count == 1) {
                                                generatedKeys = preparedStatement.getGeneratedKeys();
                                                if (generatedKeys != null && generatedKeys.next()) {
                                                    voucher.getTimeLine().setId(generatedKeys.getInt(1));
                                                    preparedStatement.clearParameters();
                                                    //Adding new meal plan
                                                    preparedStatement = Main.DATA_SOURCE_CONNECTION.prepareStatement("insert\n" +
                                                            "\tinto\n" +
                                                            "\tmeal_plan (bb,\n" +
                                                            "\thb,\n" +
                                                            "\tfb,\n" +
                                                            "\tlunch,\n" +
                                                            "\tdinner,\n" +
                                                            "\txtra_direct)\n" +
                                                            "values (?,\n" +
                                                            "?,\n" +
                                                            "?,\n" +
                                                            "?,\n" +
                                                            "?,\n" +
                                                            "?);", PreparedStatement.RETURN_GENERATED_KEYS);
                                                    preparedStatement.setBoolean(1, voucher.getMealPlan().getB_b() != null && voucher.getMealPlan().getB_b());
                                                    preparedStatement.setBoolean(2, voucher.getMealPlan().getH_b() != null && voucher.getMealPlan().getH_b());
                                                    preparedStatement.setBoolean(3, voucher.getMealPlan().getF_b() != null && voucher.getMealPlan().getF_b());
                                                    preparedStatement.setBoolean(4, voucher.getMealPlan().getLunch() != null && voucher.getMealPlan().getLunch());
                                                    preparedStatement.setBoolean(5, voucher.getMealPlan().getDinner() != null && voucher.getMealPlan().getDinner());
                                                    preparedStatement.setBoolean(6, voucher.getMealPlan().getXtra_direct() != null && voucher.getMealPlan().getXtra_direct());
                                                    count = preparedStatement.executeUpdate();
                                                    if (count == 1) {
                                                        generatedKeys = preparedStatement.getGeneratedKeys();
                                                        if (generatedKeys != null && generatedKeys.next()) {
                                                            voucher.getMealPlan().setId(generatedKeys.getInt(1));
                                                            preparedStatement.clearParameters();
                                                            //Adding new voucher
                                                            preparedStatement = Main.DATA_SOURCE_CONNECTION.prepareStatement("insert\n" +
                                                                    "\tinto\n" +
                                                                    "\tvoucher (hotelId,\n" +
                                                                    "\tclientId,\n" +
                                                                    "\tSTATUS,\n" +
                                                                    "\tcountId,\n" +
                                                                    "\troomId,\n" +
                                                                    "\tunitId,\n" +
                                                                    "\tplanId,\n" +
                                                                    "\tremarks,\n" +
                                                                    "\tpaid_by) values (?,\n" +
                                                                    "\t?,\n" +
                                                                    "\t?,\n" +
                                                                    "\t?,\n" +
                                                                    "\t?,\n" +
                                                                    "\t?,\n" +
                                                                    "\t?,\n" +
                                                                    "\t?,\n" +
                                                                    "\t?);", PreparedStatement.RETURN_GENERATED_KEYS);
                                                            preparedStatement.setInt(1, voucher.getHotel().getId());
                                                            preparedStatement.setInt(2, voucher.getClient().getId());
                                                            preparedStatement.setString(3, voucher.getStatus());
                                                            preparedStatement.setInt(4, voucher.getHeadCount().getId());
                                                            preparedStatement.setInt(5, voucher.getRoomType().getId());
                                                            preparedStatement.setInt(6, voucher.getTimeLine().getId());
                                                            preparedStatement.setInt(7, voucher.getMealPlan().getId());
                                                            preparedStatement.setString(8, (voucher.getRemarks() == null ? null : voucher.getRemarks()));
                                                            preparedStatement.setString(9, voucher.getPaidBy());
                                                            count = preparedStatement.executeUpdate();
                                                            isOkay = (count == 1);
                                                            if (isOkay) {
                                                                generatedKeys = preparedStatement.getGeneratedKeys();
                                                                if (generatedKeys != null && generatedKeys.next()) {
                                                                    voucher.setId(generatedKeys.getInt(1));
                                                                    generatedKeys.close();
                                                                    System.out.println("voucher = " + voucher);
                                                                    create_log(voucher.getId(), new Log("Created voucher", "It was successful"));
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                preparedStatement.close();
                if (!isOkay) {
                    Main.DATA_SOURCE_CONNECTION.rollback(savepoint);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Thread(write_stack_trace(e)).start();
            Platform.runLater(() -> programmer_error(e).show());
        }
        return isOkay;
    }

    protected final void create_log(Integer voucherId, Log log) {
        if (Main.DATA_SOURCE_CONNECTION == null) {
            return;
        }
        try {
            PreparedStatement preparedStatement = Main.DATA_SOURCE_CONNECTION.prepareStatement("insert\n" +
                    "into\n" +
                    "\tlogs (voucherId,\n" +
                    "\t`timestamp`,\n" +
                    "\tinfo)\n" +
                    "values (?,\n" +
                    "?,\n" +
                    "?);");
            preparedStatement.setInt(1, voucherId);
            preparedStatement.setString(2, log.getTimeStamp());
            preparedStatement.setString(3, log.getAction().concat(": ").concat(log.getInfo()));
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            new Thread(write_stack_trace(e)).start();
        }
    }

}
