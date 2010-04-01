package org.jaudiotagger.issues;

import org.jaudiotagger.AbstractTestCase;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.KeyNotFoundException;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.datatype.Artwork;
import org.jaudiotagger.tag.id3.valuepair.ImageFormats;
import org.jaudiotagger.tag.reference.PictureTypes;

import java.io.File;

/**
  Vorbis Comment reading new Image Format
*/
public class Issue286Test extends AbstractTestCase
{
    /*
     * TestRead Vorbis COverArt One
     * @throws Exception
     */
    public void testReadVorbisCoverartOne() throws Exception
    {
        File file = new File("testdata", "test76.ogg");
        AudioFile af = AudioFileIO.read(file);
        assertEquals(1,af.getTag().getArtworkList().size());
        Artwork artwork = af.getTag().getFirstArtwork();
        System.out.println(artwork);
        assertEquals(600,artwork.getImage().getWidth());
        assertEquals(800,artwork.getImage().getHeight());
        assertEquals("image/jpeg",artwork.getMimeType());
        assertEquals(3,artwork.getPictureType());

    }

    /*
     * TestRead Vorbis CoverArt Two
     * @throws Exception
     */
    public void testReadVorbisCoverartTwo() throws Exception
    {
        File file = new File("testdata", "test77.ogg");
        AudioFile af = AudioFileIO.read(file);
        assertEquals(1,af.getTag().getArtworkList().size());
        Artwork artwork = af.getTag().getFirstArtwork();
        System.out.println(artwork);
        assertEquals(600,artwork.getImage().getWidth());
        assertEquals(800,artwork.getImage().getHeight());
        assertEquals("image/jpeg",artwork.getMimeType());
        assertEquals(3,artwork.getPictureType());

    }

    /**
     * Test reading/writing artwork to Ogg
     */
    public void testReadWriteArtworkFieldsToOggVorbis()
    {
        File testFile = null;
        Exception exceptionCaught = null;
        try
        {
            testFile = AbstractTestCase.copyAudioToTmp("test3.ogg");

            //Read File okay
            AudioFile af = AudioFileIO.read(testFile);
            Tag tag = af.getTag();

            assertEquals(1, tag.getArtworkList().size());
            assertTrue(tag.getArtworkList().get(0) instanceof Artwork);
            Artwork artwork = tag.getFirstArtwork();
            assertEquals("image/png", artwork.getMimeType());
            assertNotNull(artwork.getImage());
            assertEquals("",artwork.getDescription());
            assertEquals(200, artwork.getImage().getWidth());

            //Now add new image
            Artwork newartwork = Artwork.createArtworkFromFile(new File("testdata", "coverart.png"));
            newartwork.setDescription("A new file");
            assertTrue(ImageFormats.isPortableFormat(newartwork.getBinaryData()));
            tag.addField(newartwork);
            af.commit();
            af = AudioFileIO.read(testFile);
            tag = af.getTag();
            assertEquals(2, tag.getArtworkList().size());


            assertTrue(tag.getArtworkList().get(0) instanceof Artwork);
            artwork = tag.getFirstArtwork();
            assertEquals("image/png", artwork.getMimeType());
            assertNotNull(artwork.getImage());
            assertEquals("",artwork.getDescription());
            assertEquals(200, artwork.getImage().getWidth());

            assertTrue(tag.getArtworkList().get(1) instanceof Artwork);
            artwork = tag.getArtworkList().get(1);
            assertEquals("image/png", artwork.getMimeType());
            assertNotNull(artwork.getImage());
            assertEquals("A new file",artwork.getDescription());
            assertEquals(200, artwork.getImage().getWidth());


        }
        catch (Exception e)
        {
            e.printStackTrace();
            exceptionCaught = e;
        }

        assertNull(exceptionCaught);
    }

}