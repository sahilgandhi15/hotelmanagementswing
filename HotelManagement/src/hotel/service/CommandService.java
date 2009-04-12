package hotel.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class CommandService {
	
	private static final CommandService instance = new CommandService();
	
	private static final ThreadLocal context = new ThreadLocal();

	private CommandService() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					String mysqlPath = getMysqlPath();
					String command = "cmd /c " + mysqlPath + "/mysql5/bin/mysqladmin -u root shutdown";
					Process process = runCommand(command, true);
					int exitValue = process.waitFor();
					System.out.println("Exit Value: " + exitValue);
					process.destroy();
					if (exitValue != 0) {
						throw new RuntimeException("数据库关闭失败");
					}
				} catch (InterruptedException e) {
					throw new RuntimeException();
				} catch (Exception e) {
					throw new RuntimeException();
				} 
			}
		});
		//启动数据库
		try {
			
			String mysqlPath = this.getMysqlPath();
			
			String command = "cmd /c " + mysqlPath + "/mysql5/bin/mysqld";
			Process process = runCommand(command, false);
			process.destroy();
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	private String getMysqlPath() {
		URL r = this.getClass().getClassLoader().getResource("messages_zh_CN.properties");
		String resourcePath = r.getPath();
		resourcePath = resourcePath.replaceAll("/messages_zh_CN.properties", "");
		resourcePath = resourcePath.substring(resourcePath.indexOf("/") + 1, resourcePath.lastIndexOf("/"));
		return resourcePath;
	}

	/**
	 * @param command
	 * @return
	 * @throws IOException
	 */
	private Process runCommand(String command, boolean log) {
		DosCommandThread dt = new DosCommandThread(command, log);
		dt.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return dt.getProcess();
	}
	
	private static class DosCommandThread extends Thread {
		private String dosCommand;
		private Process process;
		private boolean log;
		public DosCommandThread(String dosCommand, boolean log) {
			this.dosCommand = dosCommand;
			this.log = log;
		}

		@Override
		public void run() {
			Runtime run = Runtime.getRuntime();
			try {
				Process process = run.exec(dosCommand);
				if (log) {
					// showConsole(process);
					showErrorConsole(process);
				}
				this.process = process;
			} catch (IOException e) {
				throw new RuntimeException(e);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		private void showErrorConsole(Process process) throws Exception {
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			String line = "";
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		}
		
		public Process getProcess() {
			return process;
		}
		
	}

	public static CommandService getInstance() {
		return instance;
	}
	
	public CommandService open() {
		ExecutionContext executionContext = new ExecutionContext();
		context.set(executionContext);
		return instance;
	}
	
	public Object execute(Command command) {
		open();
		try {
			return command.execute(getCurrentExecutionContext());
		} catch (Exception e) {
			getCurrentExecutionContext().setRollbackOnly();
			throw new RuntimeException(e);
		} finally {
			close();
		}
	}
	
	public void close() {
		ExecutionContext executionContext = getCurrentExecutionContext();
		executionContext.close();
		context.remove();
	}
	
	public static ExecutionContext getCurrentExecutionContext() {
		return (ExecutionContext) context.get();
	}
	
	public Object assignId(Object obj) {
		open();
		try {
			if (getCurrentExecutionContext() != null) {
				return getCurrentExecutionContext().getSession().save(obj);
			}
			return null;
		} catch (Exception e) {
			getCurrentExecutionContext().setRollbackOnly();
			throw new RuntimeException(e);
		} finally {
			close();
		}
	}
}
