package com;

import io.dropwizard.setup.Bootstrap;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;

public class MyCommand extends io.dropwizard.cli.Command {

    public MyCommand() {
        super("name", "description");
    }

    @Override
    public void configure(Subparser subparser) {

    }

    @Override
    public void run(Bootstrap<?> bootstrap, Namespace namespace) throws Exception {

    }
}
