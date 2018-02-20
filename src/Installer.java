import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Installer
{

	public Installer()
	{

	}

	public static void main(String[] args) throws MalformedURLException
	{
		String sUrl = "https://drive.google.com/uc?authuser=0&id=1U7XsvO-v0ZsqVFjK0iaKm-vJ34G43nya&export=download";

		URL url = new URL(sUrl);

		File file = new File("Zahlensysteme.jar");

		Installer.copyURLToFile(url, file);

	}

	public static void copyURLToFile(URL url, File file)
	{

		try
		{
			InputStream input = url.openStream();
			if (file.exists())
			{
				if (file.isDirectory())
					throw new IOException("File '" + file + "' is a directory");

				if (!file.canWrite())
					throw new IOException("File '" + file
							+ "' cannot be written");
			} else
			{
				File parent = file.getParentFile();
				if ((parent != null) && (!parent.exists())
						&& (!parent.mkdirs()))
				{
					throw new IOException("File '" + file
							+ "' could not be created");
				}
			}

			FileOutputStream output = new FileOutputStream(file);

			byte[] buffer = new byte[4096];
			int n = 0;
			while (-1 != (n = input.read(buffer)))
			{
				output.write(buffer, 0, n);
			}

			input.close();
			output.close();

			System.out.println("File '" + file + "' downloaded successfully!");
		} catch (IOException ioEx)
		{
			ioEx.printStackTrace();
		}
	}

}
