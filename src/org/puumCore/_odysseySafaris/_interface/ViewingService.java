/*
 * Copyright (c) Puum Core 2022.
 */

package org.puumCore._odysseySafaris._interface;

import javafx.fxml.Initializable;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author Puum Core<br>
 * <a href = https://github.com/puumCore>GitHub: Mandela Muriithi</a>
 * @version 1.0
 * @since 12/11/2021
 */

public interface ViewingService extends Initializable {

    ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newScheduledThreadPool(1);

    Runnable auto_update_search_suggestions();

}
