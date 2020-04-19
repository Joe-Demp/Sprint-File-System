package commands;

import util.actions.FileSystemAction;

/**
 * Template for actions that cause changes to the filesystem.
 * The implementors should have a single constructor with a {@code File} as its first argument.
 * The second argument, if present, should be a String.
 */
public interface Command extends FileSystemAction {
    @Override
    CommandResponse execute();
}
