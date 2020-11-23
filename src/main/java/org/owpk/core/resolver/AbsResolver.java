package org.owpk.core.resolver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;

public abstract class AbsResolver<E> {
    protected static final Logger log = LogManager.getLogger(EntityResolver.class);
    protected String[] args;

    public AbsResolver(String[] args) {
        CommandLine.ParseResult parseResult = new CommandLine(this).parseArgs(args);
        log.info("Options: " + parseResult.originalArgs());
        this.args = args;
    }

    public abstract void resolve(E data);

}
