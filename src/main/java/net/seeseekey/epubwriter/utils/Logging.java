package net.seeseekey.epubwriter.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class to get logger
 */
public final class Logging {

    private Logging() {
    }

    public static Logger getLogger() {

        StackWalker.StackFrame frame = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
                .walk(stream -> stream.skip(1)
                        .findFirst()
                        .orElse(null));

        if (frame == null) {
            return LoggerFactory.getLogger("Common");
        }

        return LoggerFactory.getLogger(frame.getClassName());
    }
}
