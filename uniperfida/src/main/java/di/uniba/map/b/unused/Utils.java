/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package di.uniba.map.b.unused;
// package di.uniba.map.b.uniperfida;
 // work in progress
/*
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Utils {

    // questo metodo legge il file riga per riga e mette ogni riga del file all'interno dell'insieme di stringhe. In questo modo posso modificare il file senza utilizzare il codice
        public static Set<String> loadFileListInSet(File file) throws IOException {
        Set<String> set = new HashSet<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        while (reader.ready()) {
            set.add(reader.readLine().trim().toLowerCase());
        }
        reader.close();
        return set;
    }

    // questo metodo fa lo split della stringa sugli spazi, cicla sul risultato di questo split, se la parola che trova non e' presente nelle stopwords la aggiunge al token e restituisce la lista di token
    public static List<String> parseString(String string, Set<String> stopwords) {
        List<String> tokens = new ArrayList<>();
        String e = "e";
        String o = "o";
        String con = "con";
        String[] split = string.toLowerCase().split("'|\\s+"); // fa lo split della stringa sugli spazi e sugli apostrofi (da vedere)
        for (String t : split) { // cicla sul risultato di questo split
            if (!stopwords.contains(t) || t.contains(e) || t.contains(o)) { // se la parola che trova non e' presente nelle stopwords
                tokens.add(t); // la aggiunge al token
                if (t.contains(con)) {
                    tokens.remove(t);
                }
            }
        }
        return tokens; // restituisce la lista di token restituisce la lista di token
    }

}

*/