package commands;

import util.actions.FileSystemAction;

/**
 * Template for actions that cause changes to the filesystem
 */
public interface Command extends FileSystemAction {
    @Override
    CommandResponse execute();
}
