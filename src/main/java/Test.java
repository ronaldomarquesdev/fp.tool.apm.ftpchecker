import java.io.BufferedReader ;
import java.io.IOException ;
import java.io.InputStreamReader ;
import java.io.UnsupportedEncodingException ;



public class Test {
	
	public static void main(String[] args) {
		
		// String pathFile = "./src/main/resources/data/ftp-list.tcsv";
		/*
		 * Path pathFile = null; try { pathFile =
		 * Paths.get(Test.class.getResource("ftp-list.tcsv").toURI()); } catch
		 * (Exception e1) { DP.pdln("erro URI syntax " + e1.getMessage()); }
		 */
		// String pathFile = "./ftp-list2.tcsv";
		
		/*
		 * try { FileReader fr; fr = new
		 * FileReader("./src/main/resources/data/ftp-list.tcsv"); BufferedReader br =
		 * new BufferedReader(fr);
		 * String str = br.readLine(); while ((str != null)) { System.out.println(str);
		 * str = br.readLine(); } br.close(); fr.close();
		 * } catch (FileNotFoundException e) { DP.pdln("erro file not found " +
		 * e.getMessage()); } catch (IOException e) { DP.pdln("erro io exception " +
		 * e.getMessage()); }
		 */
		
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(Test.class.getResourceAsStream("data/ftp-list.tcsv"), "UTF-8")) ;
			String line = null ;
			
			while ((line = br.readLine()) != null) {
				System.out.printf("\n%s", line) ;
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace() ;
		} catch (IOException e) {
			e.printStackTrace() ;
		}
		
		/*
		 * List<String> lines = new ArrayList<String>();
		 * int skip = 1; try { Files.lines(pathFile).skip(skip).forEach(s ->
		 * lines.add(s)); } catch (IOException e) { DP.pdln("erro Paths.get " +
		 * e.getMessage()); }
		 * // tcsvReader.getLines().stream().forEach(linha -> {
		 * lines.stream().forEach(linha -> { System.out.printf("\n%s\n", linha); });
		 */
		
	}
	
}
