package commands;

import util.FileSystemAction;

/**
 * Template for actions that cause changes to the filesystem
 */
public interface Command extends FileSystemAction {
    CommandResponse execute();
}
