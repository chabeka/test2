package fr.urssaf.image.commons.util.compress;

import java.io.File;
import java.io.IOException;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipUtils;
import org.apache.commons.io.FilenameUtils;

import fr.urssaf.image.commons.util.file.FileWriterUtil;

/**
 * Cette classe utilitaire propose l'archivage zip tar gz tgz
 * 
 * @author Bertrand BARAULT
 * 
 */
public final class CompressUtil {

   private CompressUtil() {

   }

   /**
    * compression avec zip
    * 
    * @param archiveName
    *           nom de l'archive (avec une extension .zip)
    * @param fileName
    *           nom du fichier à compresser
    * @return checksum en crc32 du zip
    * @throws IOException
    *            exception sur le fichier
    */
   public static long zip(String archiveName, String fileName)
         throws IOException {
      return zip(archiveName, fileName, new String[0]);
   }

   /**
    * compression avec zip
    * 
    * @param archiveName
    *           nom de l'archive (avec une extension .zip)
    * @param fileName
    *           nom du fichier à compresser
    * @param extensions
    *           filtre sur les extensions (text,java,xml)
    * @return checksum en crc32 du zip
    * @throws IOException
    *            exception sur le fichier
    */
   public static long zip(String archiveName, String fileName,
         String... extensions) throws IOException {

      ZipCompressOutputStream outputStream = new ZipCompressOutputStream(
            archiveName, fileName);
      outputStream.setExtensions(extensions);
      long checksum = outputStream.compress();

      return checksum;

   }

   /**
    * compression avec tar
    * 
    * @param archiveName
    *           nom de l'archive (avec une extension .tar)
    * @param fileName
    *           nom du fichier à compresser
    * @return checksum en crc32 du tar
    * @throws IOException
    *            exception sur le fichier
    */
   public static long tar(String archiveName, String fileName)
         throws IOException {

      return tar(archiveName, fileName, new String[0]);
   }

   /**
    * compression avec tar
    * 
    * @param archiveName
    *           nom de l'archive (avec une extension .tar)
    * @param fileName
    *           nom du fichier à compresser
    * @param extensions
    *           filtre sur les extensions (text,java,xml)
    * @return checksum en crc32 du tar
    * @throws IOException
    *            exception sur le fichier
    */
   public static long tar(String archiveName, String fileName,
         String... extensions) throws IOException {

      TarCompressOutputStream outputStream = new TarCompressOutputStream(
            archiveName, fileName);
      outputStream.setExtensions(extensions);

      long checksum = outputStream.compress();

      return checksum;

   }

   /**
    * compression avec tgz
    * 
    * @param path
    *           chemin de l'archive
    * @param fileName
    *           nom du fichier à compresser
    * @return checksum en crc32 du tgz
    * @throws IOException
    *            exception sur le fichier
    */
   public static long tgz(String path, String filename) throws IOException {

      return tgz(path, filename, new String[0]);
   }

   /**
    * compression avec tgz
    * 
    * @param path
    *           chemin de l'archive
    * @param filename
    *           nom du fichier à compresser
    * @param extensions
    *           iltre sur les extensions (text,java,xml)
    * @return checksum en crc32 du tgz
    * @throws IOException
    *            exception sur le fichier
    */
   public static long tgz(String path, String filename, String... extensions)
         throws IOException {

      String tar = FilenameUtils.concat(path, FilenameUtils
            .getBaseName(filename)
            + ".tar");
      tar(tar, filename, extensions);

      long checksum = gzip(path, tar);

      // suppression du fichier tar
      new File(tar).delete();

      return checksum;
   }

   /**
    * compression avec gz
    * 
    * @param path
    *           chemin de l'archive
    * @param fileName
    *           nom du fichier à compresser
    * @return checksum en crc32 du gz
    * @throws IOException
    *            exception sur le fichier
    */
   public static long gzip(String path, String fileName) throws IOException {

      String compressFileName = FilenameUtils.concat(path, FilenameUtils
            .getName(GzipUtils.getCompressedFilename(fileName)));

      GzCompressOutputStream outputStream = new GzCompressOutputStream(
            compressFileName, fileName);

      long checksum = outputStream.compress();

      return checksum;

   }

   protected static void copy(File file, ArchiveOutputStream out,
         ArchiveEntry entry) throws IOException {

      // ajout de cette entrée dans le flux d'écriture de l'archive Zip
      out.putArchiveEntry(entry);

      FileWriterUtil.copy(file, out);

      // Close the current entry
      out.closeArchiveEntry();

   }

   protected static String entry(String path, File file) throws IOException {

      File pathFile = new File(path);

      String name;

      if (pathFile.isFile()) {
         name = file.getName();
      } else {

         name = file.getAbsolutePath().substring(
               FilenameUtils.getFullPath(pathFile.getAbsolutePath()).length());
      }

      return name;

   }
}