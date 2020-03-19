package me.d3monw3st.prisoncount;

import java.util.logging.Logger;

public class Console {

        private static final String prefix = "[Prison Counter] ";
        private static Logger logger = Logger.getLogger("PrisonCounter");

        public static void log(String message) {
            logger.info(prefix + message);
        }

        public static void warn(String message) {
            logger.warning(prefix + message);
        }

}
