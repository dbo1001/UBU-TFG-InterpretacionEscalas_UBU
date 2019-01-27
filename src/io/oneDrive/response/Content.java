package io.oneDrive.response;

import java.util.Arrays;

/**
 * Directory content.
 * 
 * @author Raúl Marticorena
 * @since 1.0
 */
public class Content {

	/** Drive items. Can be folder or file. */
	private DriveItem[] value;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Content [value=").append(Arrays.toString(value)).append("]");
		return builder.toString();
	}
}

/**
 * Drive item. Can be a file or a folder.
 * 
 * @author Raúl Marticorena
 * @since 1.0
 */
class DriveItem {
	
	private String id;
	private String name;
	private Folder folder;
	private File file;
	private ParentReference parentReference;
	
	/**
	 * Checks if its a file.
	 * 
	 * @return true if it is a file
	 */
	public boolean isFile() {
		return file != null;
	}
	
	/**
	 * Checks if its a directory.
	 * 
	 * @return true if it is a directory
	 */
	public boolean isDirectory() {
		return folder != null;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DriveItem [id=").append(id).append(", name=").append(name).append(", folder=").append(folder)
				.append(", file=").append(file).append(", parentReference=").append(parentReference).append("]");
		return builder.toString();
	}
}

/**
 * Folder.
 * 
 * @author Raúl Marticorena
 * @since 1.0
 */
class Folder {
	/** Number of children. */
	private int childCount;

	/**
	 * Gets the child count. 
	 * 
	 * @return child count
	 */
	public int getChildCount() {
		return childCount;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Folder [childCount=").append(childCount).append("]");
		return builder.toString();
	}
}

/**
 * File.
 * 
 * @author Raúl Marticorena
 * @since 1.0
 */
class File {
	/** Mime type. */
	private String mimeType;

	/**
	 * Gets mime type.
	 * 
	 * @return mimeType
	 */
	public String getMimeType() {
		return mimeType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("File [mimeType=").append(mimeType).append("]");
		return builder.toString();
	}
}

/**
 * Parent reference.
 * 
 * @author Raúl Marticorena
 * @since 1.0
 */
class ParentReference {
	
	private String name;
	private String id;
	private String path;

	/**
	 * Gets name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets id.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Gets path.
	 * 
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ParentReference [name=").append(name).append(", id=").append(id).append(", path=").append(path)
				.append("]");
		return builder.toString();
	}
}
