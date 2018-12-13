/* GameSounds plays from the JLayer library */
package lazarus;

// Imports IO Exception library
import java.io.IOException;

// imports libraries from sound.sampled.*
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

// GameSounds class creates new sound loops cased on different sound files
public class GameSounds {
    public static final GameSounds sounds = new GameSounds();
    
    // Plays a given sound loop. If not, it throws an exception error
    public static void playLoop(String filename)
    {
        try
        {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(LazarusWorld.class.getResource(filename)));
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        catch (Exception exc)
        {
            exc.printStackTrace(System.out);
        }
    }
    
    // Plays a given sound clip. If not, it throws an exception error
    public static void playClip(String filename)
    {
        try
        {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(LazarusWorld.class.getResource(filename)));
            clip.loop(0);
        }
        catch (Exception exc)
        {
            exc.printStackTrace(System.out);
        }
    }
    
    // Creates a new thread to try and get the file to play. If not, an error is thrown
    public static void play(final String filename)
    {
      try {
        // Play now. 
        new Thread() {
            public void run() {
            	try { 
                AudioInputStream in= AudioSystem.getAudioInputStream(LazarusWorld.class.getResource(filename));
                AudioInputStream din = null;
                AudioFormat baseFormat = in.getFormat();
                AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(),false);
                din = AudioSystem.getAudioInputStream(decodedFormat, in);
                rawplay(decodedFormat, din);
                in.close();
            	}

                catch (Exception e) {
                	System.out.println("Error playing " + filename); e.printStackTrace();
                }
            }
        }.start();
      } 
      catch (Exception e) {
          System.out.println("Error playing " + filename); e.printStackTrace();
      }
    }

    // Similar to play but plays the raw version of the soundfile
    private static void rawplay(AudioFormat targetFormat, AudioInputStream din) throws IOException,                                                                                                LineUnavailableException
    {
      byte[] data = new byte[4096];
      SourceDataLine line = getLine(targetFormat); 
      if (line != null)
      {
        // Start
        line.start();
        int nBytesRead = 0, nBytesWritten = 0;
        while (nBytesRead != -1)
        {
            nBytesRead = din.read(data, 0, data.length);
            if (nBytesRead != -1) nBytesWritten = line.write(data, 0, nBytesRead);
        }
        // Stop
        line.drain();
        line.stop();
        line.close();
        din.close();
      } 
    }

    // gets the line
    private static SourceDataLine getLine(AudioFormat audioFormat) throws LineUnavailableException
    {
      SourceDataLine res = null;
      DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
      res = (SourceDataLine) AudioSystem.getLine(info);
      res.open(audioFormat);
      return res;
    } 
}