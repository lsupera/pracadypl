/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaspice;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.Math;

/**
 *
 * @author jstar
 */
public class Tools {

	public static RawFileContent rawFileReader(String fileName) throws Exception {

		String[] keys = { "title", "date", "plotname", "flags", "no. variables", "no. points", "variables", "binary" };
		Arrays.sort(keys);
		BufferedReader b = new BufferedReader(new FileReader(fileName));
		String l;
		int n = 0;
		Map<String, String> head = new HashMap<>();
		List<Map.Entry<String, String>> vars = null;
		while ((l = b.readLine()) != null & !(l.equals("Binary:"))) {
			// Solely for diagnostics: System.out.println(l);
			n += l.length() + 1;
			String[] f = l.split(":", 2);
			// Solely for diagnostics: System.out.println(f.length);
			if (f.length == 2) {
				int nk = Arrays.binarySearch(keys, f[0].toLowerCase());
				if (nk < 0) {
					throw new NotImplementedException("unknown key " + f[0]);
				}
				if (f[0].toLowerCase().equals("variables")) {
					int noVars = Integer.parseInt(head.get("no. variables"));
					vars = new ArrayList<>();
					for (int i = 0; i < noVars; i++) {
						l = b.readLine();
						n += l.length() + 1;
						f = l.trim().split("\\s+");
						if (f.length == 3 || f.length == 4) {
							vars.add(new AbstractMap.SimpleEntry(f[1].trim(), f[2].trim()));
						}
					}
				} else {
					head.put(f[0].toLowerCase(), f[1].trim());
				}
			}
		}
		n += l.length() + 1;
		b.close();
		// System.out.println(n + " chars up to " + l);

		if (!head.get("flags").equals("real")) {

			DataInputStream in = new DataInputStream(new FileInputStream(fileName));
			byte c;
			while (n > 1) {
				c = in.readByte();
				// Solely for diagnostics: System.out.print((char) c);
				n--;
			}
			c = in.readByte();
			// Solely for diagnostics: System.out.println("\nLast byte " + (char) c);
			int noVars = Integer.parseInt(head.get("no. variables"));
			int noPoints = Integer.parseInt(head.get("no. points"));
			double[][] seriesReal = new double[noVars][noPoints];
			double[][] seriesImaginary = new double[noVars][noPoints];
			double[][] seriesModule = new double[noVars][noPoints];
			double[][] seriesPhase = new double[noVars][noPoints];
			byte[] tempByte = new byte[8];
			
			for (int j = 0; j < noPoints; j++) {
				for (int i = 0; i < 2*noVars; i++) {
					for (int bn=0;bn < 8; bn++) {
						tempByte[(int) bn] = in.readByte();
					}

					if (i%2 == 0) {
						seriesReal[i/2][j] = ByteBuffer.wrap(tempByte).order(ByteOrder.LITTLE_ENDIAN).getDouble();
						System.out.println("realpart"+i/2+","+j+"--"+seriesReal[i/2][j]);
						// Solely for diagnostics:
					} else {
						seriesImaginary[i/2][j] = ByteBuffer.wrap(tempByte).order(ByteOrder.LITTLE_ENDIAN).getDouble();
						System.out.println("imaginary part"+i/2+","+j+"--"+seriesImaginary[i/2][j]);
					}

					/*
					 * if (j == 1 || j == noPoints - 1) { System.out.println("(" + i + "," + j +
					 * ")->" + series[i][j]); }
					 */

				}
			}

			for (int j = 0; j < noPoints; j++) {
				for (int i = 0; i < noVars; i++) {
					seriesModule[i][j] = java.lang.Math.sqrt(seriesReal[i][j]*seriesReal[i][j]+seriesImaginary[i][j]*seriesImaginary[i][j]);
					System.out.println("module"+"("+i+","+j+")"+seriesModule[i][j]);
					seriesPhase[i][j]= java.lang.Math.atan(seriesImaginary[i][j]*seriesReal[i][j]);
					System.out.println("phase"+"("+i+","+j+")"+seriesPhase[i][j]);
					
				}
			}

			RawFileContent ret = new RawFileContent(head, vars, seriesModule, seriesPhase);
			try {
				c = in.readByte();
				// Solely for diagnostics:
				/*
				 * if( c != '\n' ) { System.out.print((char) c); throw new java.io.
				 * StreamCorruptedException("Incorrect file format: bytes past the end of data"
				 * ); }
				 */
			} catch (EOFException e) {
				// that's ok, we should have EOF past all data
			} finally {
				in.close();
			}
			return ret;

			// throw new NotImplementedException("bad file contents: not real numbers"); //
			// not real series
		}
	

	else

	{
		DataInputStream in = new DataInputStream(new FileInputStream(fileName));
		byte c;
		while (n > 1) {
			c = in.readByte();
			// Solely for diagnostics: System.out.print((char) c);
			n--;
		}
		c = in.readByte();
		// Solely for diagnostics: System.out.println("\nLast byte " + (char) c);
		int noVars = Integer.parseInt(head.get("no. variables"));
		int noPoints = Integer.parseInt(head.get("no. points"));
		double[][] series = new double[noVars][noPoints];
		byte[] tempByte = new byte[8];
		for (int j = 0; j < noPoints; j++) {
			for (int i = 0; i < noVars; i++) {
				for (int bn = 0; bn < 8; bn++) {
					tempByte[bn] = in.readByte();
				}

				series[i][j] = ByteBuffer.wrap(tempByte).order(ByteOrder.LITTLE_ENDIAN).getDouble();
				System.out.println(series[i][j]);
				// Solely for diagnostics:

				if (j == 1 || j == noPoints - 1) {
					System.out.println("(" + i + "," + j + ")->" + series[i][j]);
				}

			}
		}
		RawFileContent ret = new RawFileContent(head, vars, series);
		try {
			c = in.readByte();
			// Solely for diagnostics:
			/*
			 * if( c != '\n' ) { System.out.print((char) c); throw new java.io.
			 * StreamCorruptedException("Incorrect file format: bytes past the end of data"
			 * ); }
			 */
		} catch (EOFException e) {
			// that's ok, we should have EOF past all data
		} finally {
			in.close();
		}
		return ret;
	}
	}


	public static void main(String[] args) throws Exception {
		RawFileContent rc = Tools.rawFileReader(args[0]);
		System.out.println(rc);
	}

}
