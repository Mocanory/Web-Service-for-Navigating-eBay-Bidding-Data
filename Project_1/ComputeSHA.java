import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.*;

public class ComputeSHA {

	private static final long BUFFER_SIZE = 1024;

	public static void main(String[] args){

		if (args.length != 1) {
			System.err.println("ERROR: invalid command.");
			return;
		}

		File file = new File(args[0]);
		if (!file.exists() || !file.isFile()) {
			System.err.println("Error: fail to open file.");
			return;
		}

		try{
			FileInputStream in = new FileInputStream(file);
			FileChannel channel = in.getChannel();
			MappedByteBuffer buff = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
			byte[] b = new byte[(int) BUFFER_SIZE];

			try{
				MessageDigest md = MessageDigest.getInstance("SHA-1");
				long len = file.length();
				
				for (long offset = 0; offset < len; offset += BUFFER_SIZE) {
					if (len - offset <= BUFFER_SIZE)
						b = new byte[(int) (len - offset)];
					buff.get(b);
					md.update(b);
				}

				StringBuilder sb = new StringBuilder();
				for (byte bt : md.digest())
					sb.append(String.format("%02x", bt));

				System.out.println(sb.toString());
				return;
			} catch (Exception ex){
				System.err.println("ERROR: invalid digest algorithm.");
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
