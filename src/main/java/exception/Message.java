package exception;

public final class Message {
	
	 private Message() {
		    throw new IllegalStateException("Utility class");
		  }
	public static final String ERROR_NO_NEXT = "a aim can't have next";
	public static final String FILE_CONTENT_KO = "erreur dans les données du fichier";
	public static final String NO_CYCLE = "Shouldn't have cycle";
	public static final String NO_FILE = "There is no file";
	public static final String ONE_AND_ONLY_ONE_AIM = "You should have one and only one aim";
	public static final String TASK_NEED_NAME = "A task should have a name";
	public static final String TASK_NOT_IN_DIAGRAM = "the task is not in diagram";
	public static final String TASK_SHOULD_HAVE_NEXT = "The following tasks Should have Next :";
	public static final String DATA_ERROR = "The data are false";
}
