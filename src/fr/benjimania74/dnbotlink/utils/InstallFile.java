package fr.benjimania74.dnbotlink.utils;

import be.alexandre01.dreamnetwork.core.console.Console;
import be.alexandre01.dreamnetwork.core.console.ConsolePath;
import be.alexandre01.dreamnetwork.core.console.colors.Colors;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class InstallFile {
    public static boolean install(String file, String url) {
        try {
            URL fileURL = new URL(url);
            URLConnection conn = fileURL.openConnection();
            if(conn instanceof HttpURLConnection) {((HttpURLConnection)conn).setRequestMethod("HEAD");}
            conn.getInputStream();
            Console.print(Colors.YELLOW + "Start installation '" + file + "' from '" + url + "'");
            InputStream in = fileURL.openStream();
            Console.print(Colors.YELLOW + "Downloading of " + (conn.getContentLength() /10000) + "MB");
            Files.copy(in, Paths.get(file), StandardCopyOption.REPLACE_EXISTING);
            Console.print(Colors.YELLOW + "Installation of '" + file + "' finished");
            return true;
        }catch (IOException e){
            Console.print(Colors.RED_BACKGROUND + "Unable to download these files");
            return false;
        }
    }

    public static boolean installDNPlugin(String file){
        try {
            InputStream is = ConsolePath.Main.class.getClassLoader().getResourceAsStream("files/universal/DreamNetwork-Plugin.jar");

            try (OutputStream out = new FileOutputStream(file)) {
                byte[] buf = new byte[1024];
                int length;
                while ((length = is.read(buf)) > 0) {
                    out.write(buf, 0, length);
                }
                out.flush();
                out.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean copyLocalFile(String copiedFile, String file){
        try {
            InputStream is = new FileInputStream(copiedFile);

            try (OutputStream out = new FileOutputStream(file)) {
                byte[] buf = new byte[1024];
                int length;
                while ((length = is.read(buf)) > 0) {
                    out.write(buf, 0, length);
                }
                out.flush();
                out.close();
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean installPaper(String file, String version){
        String url = getPaperVersion(version);
        if(url.equals("invalidVersion")){return false;}
        return install(file, url);
    }

    public static boolean installBungee(String file){return install(file, "https://ci.md-5.net/job/BungeeCord/lastSuccessfulBuild/artifact/bootstrap/target/BungeeCord.jar");}

    public static boolean installFlameCord(String file, String version){
        String url = getPaperVersion(version);
        if(url.equals("invalidVersion")){return false;}
        return install(file, url);
    }

    private static String getPaperVersion(String version){
        try {
            URL url = new URL("https://api.papermc.io/v2/projects/paper/versions/" + version + "/");
            Scanner sc = new Scanner(url.openStream());
            StringBuffer sb = new StringBuffer();
            while (sc.hasNext()) {sb.append(sc.next());}
            JSONObject object = (JSONObject) new JSONParser().parse(sb.toString());

            if(!object.containsKey("builds")){return "invalidVersion";}

            JSONArray builds = (JSONArray) object.get("builds");
            long build = (long) builds.get(builds.size()-1);
            return "https://api.papermc.io/v2/projects/paper/versions/" + version + "/builds/" + build + "/downloads/paper-" + version + "-" + build + ".jar";
        }catch (Exception e){
            e.printStackTrace();
            return "invalidVersion";
        }
    }

    private static String getWaterfallVersion(String version){
        try {
            URL url = new URL("https://api.papermc.io/v2/projects/waterfall/versions/" + version + "/");
            Scanner sc = new Scanner(url.openStream());
            StringBuffer sb = new StringBuffer();
            while (sc.hasNext()) {sb.append(sc.next());}
            JSONObject object = (JSONObject) new JSONParser().parse(sb.toString());

            if(!object.containsKey("builds")){return "invalidVersion";}

            JSONArray builds = (JSONArray) object.get("builds");
            long build = (long) builds.get(builds.size()-1);
            return "https://api.papermc.io/v2/projects/waterfall/versions/" + version + "/builds/" + build + "/downloads/paper-" + version + "-" + build + ".jar";
        }catch (Exception e){
            e.printStackTrace();
            return "invalidVersion";
        }
    }
}
