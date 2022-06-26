package dev.ronaldomarques.fp.tool.apm.ftpchecker.service ;



import static dev.ronaldomarques.util.myutility.filereader.TcsvWriter.csvLineMaker ;
import java.io.BufferedReader ;
import java.io.IOException ;
import java.io.InputStream ;
import java.io.InputStreamReader ;
import java.net.SocketException ;
import java.nio.charset.StandardCharsets ;
import java.text.SimpleDateFormat ;
import java.util.Date ;
import java.util.concurrent.TimeUnit ;
import org.apache.commons.net.ftp.FTPClient ;
import dev.ronaldomarques.util.myutility.debugger.DP ;



public final class FtpCheckerService {
	
	private static int loopLimit = 0 ;
	private static int loopCount = 0 ;
	
	
	
	public static void startLooping() {
		
		startLooping(Integer.MAX_VALUE) ;
		
	}
	
	
	
	// Overload
	public static void startLooping(int limit) {
		loopCount = 0 ;
		loopLimit = limit ;
		
		loop() ;
	}
	
	
	
	private static void loop() {
		
		DP.pdln("- loop() iniciado -\n") ;
		
		printInputStreamToConsole(getFileFromResourceAsStream("data/ftp-list.tcsv")) ;
		
		DP.pdln("") ;
		
		while (loopCount < loopLimit) {
			
			// capta a lista de ptp e checa a lista de ftp
			DP.pdln("- Iniciar SCAN FTPs From IS ... -\n") ;
			scanFtpsFromInputStream(getFileFromResourceAsStream("data/ftp-list.tcsv")) ;
			
			int tempoEmSegundos = 10 ;
			
			try {
				System.out.printf("\n\n...Tempo de espera (%d) para o próximo looping.", tempoEmSegundos) ;
				TimeUnit.SECONDS.sleep(tempoEmSegundos) ;
			} catch (InterruptedException e) {
				e.printStackTrace() ;
			}
			
		}
		
	}
	
	
	
	public static void stopLooping() {
		
		loopLimit = Integer.MIN_VALUE ;
		
	}
	
	
	
	private static InputStream getFileFromResourceAsStream(String fileName) {
		
		ClassLoader classLoader = FtpCheckerService.class.getClassLoader() ; // getClass().getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream(fileName) ;
		
		if (inputStream == null) {
			throw new IllegalArgumentException("file not found [" + fileName + "]") ;
		} else {
			return inputStream ;
		}
		
	}
	
	
	
	private static void printInputStreamToConsole(InputStream is) {
		
		DP.pdln("- printInputStreamToConsole(is) iniciado... -\n") ;
		
		try (InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
				BufferedReader reader = new BufferedReader(streamReader)) {
			
			String line ;
			
			while ((line = reader.readLine()) != null) {
				System.out.println(line) ;
			}
			
		} catch (IOException e) {
			e.printStackTrace() ;
		}
		
	}
	
	
	
	public static String check(String csvLineFtpCredentials) {
		
		DP.pdln("\n\n- checkcsv(csvLine) (unitário) iniciado... -\n") ;
		
		String[] v = csvLineFtpCredentials.split(";") ;
		String alias = v[0] ;
		String host = v[1] ;
		int port = Integer.parseInt(v[2]) ;
		String username = v[3] ;
		String password = v[4] ;
		String sysType = "" ;
		String folderSet = "" ;
		String[] filesNamesCurrentDirectory ;
		StringBuilder sb = new StringBuilder() ; // FIXME: mudar este sb em arrylist ??
		
		FTPClient ftpClient = new FTPClient() ;
		
		Date dataHoraAtual = new Date() ;
		String dataFormatada = new SimpleDateFormat("yyyyMMdd").format(dataHoraAtual) ;
		String horaFormatada = new SimpleDateFormat("HHmmss").format(dataHoraAtual) ;
		
		try {
			DP.pdln("\n- Testando uma FTP do arquivo ( " + host + " )-\n") ;
			
			ftpClient.connect(host, port) ;
			TimeUnit.MILLISECONDS.sleep(200) ;
			
			ftpClient.login(username, password) ;
			TimeUnit.MILLISECONDS.sleep(200) ;
			
			ftpClient.cwd("/") ;
			TimeUnit.MILLISECONDS.sleep(200) ;
			
			sysType = ftpClient.getSystemType() ;
			TimeUnit.MILLISECONDS.sleep(200) ;
			
			filesNamesCurrentDirectory = ftpClient.listNames() ;
			TimeUnit.MILLISECONDS.sleep(400) ;
			
			if (filesNamesCurrentDirectory != null && filesNamesCurrentDirectory.length > 0) {
				folderSet = csvLineMaker(filesNamesCurrentDirectory) ;
				sb.append("[" + dataFormatada + horaFormatada + "];" + alias + ";" + host + ";" + sysType + ";"
						+ folderSet) ;
			}
			
			ftpClient.logout() ;
			ftpClient.disconnect() ;
			
		} catch (SocketException excep) {
			sb.append("[" + dataFormatada + horaFormatada + "];" + alias + ";" + host + ";" + "Socket Exception !") ;
		} catch (IOException excep) {
			sb.append("[" + dataFormatada + horaFormatada + "];" + alias + ";" + host + ";" + "I/O Exception !") ;
		} catch (InterruptedException excep) {
			sb.append(
					"[" + dataFormatada + horaFormatada + "];" + alias + ";" + host + ";" + "Interruped Exception !") ;
		}
		
		DP.pdln("Um  S.B. do (" + host + ") " + sb.toString()) ;
		
		return sb.toString() ;  // FIXME: mudar este strbldr em arrylist ???
		
	}
	
	
	
	private static void scanFtpsFromInputStream(InputStream is) {
		
		DP.pdln("- scanFtpFromInputStream(is) iniciado ... -\n") ;
		printInputStreamToConsole(is) ;
		System.out.printf("\n\n") ;
		
		try (InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
				BufferedReader reader = new BufferedReader(streamReader)) {
			
			String line ;
			
			while ((line = reader.readLine()) != null) {
				DP.pdln("\n\n- Iniciar o CHECK da linha [ " + line + " ] -\n") ;
				System.out.printf("\n%s\n", check(line)) ;
			}
			
		} catch (IOException e) {
			e.printStackTrace() ;
		}
		
	}
	
}
