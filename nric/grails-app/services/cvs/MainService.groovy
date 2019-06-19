package cvs


class MainService {

    File toFile(def stuff) {
        String name = stuff.getOriginalFilename().replace(" ", "")

        if (!name.endsWith("pdf")) {
            File convFile = new File("/model/" + name);
            stuff.transferTo(convFile);

            convFile
        } else {
            String filename = pdfToFile(stuff)
            String cmd = "python2.7 /model/crop_to_target.py /model/$filename"
            println(cmd)
            def proc = cmd.execute()
            proc.waitFor()


            new File("/model/" + filename);
        }

    }

    String processFile(String filename) {
        String name = filename.replace(".", '-') + "_${System.currentTimeMillis()}"
        String cmd = "python2.7 /model/face.py /model/$filename /apps/home/grails-app/assets/images/$name"
        def proc = cmd.execute()
        proc.waitFor()

        name
    }

    String ocr(String file) {
        /*StringBuilder pred = new StringBuilder()

        try {
            def proc = "tesseract /model/${file} /tmp/out --oem 1".execute()
            proc.waitFor()
            def ypred = new File("/tmp/out.txt").getText('UTF-8').split("\n")

            for (String line : ypred) {
                pred.append(line)
                pred.append("\n")
            }

            return pred.toString()

        } catch (Exception e) {
            throw new Exception(e)
        }*/
        ocr(file, "/model/")
    }

    ArrayList<String> names(String ocr, String nric) {
        /*String trimmed = ocr.trim()

        if (!nric.empty) {
            trimmed = trimmed.replace(nric, "")
        }
        ArrayList<String> names = new ArrayList<>()

        trimmed.split("\n").each { line ->
            println("Line: $line")
            if (!line.trim().empty) {
                names.addAll(line.split(' '))
                String next = line.next()
                if (!next.trim().empty && line.trim().split(" ").size() == 1)
                    names.add(next.trim())
                return
            }
        }

        names*/
        names(ocr)
    }

    ArrayList<String> names(String ocr) {
        String trimmed = ocr.trim()

        ArrayList<String> names = new ArrayList<>()

        trimmed.split("\n").each { line ->
            println("Line: $line")
            if (!line.trim().empty) {
                if (line.split(' ').size() >= 3 && line.split(' ').size() <= 4) {
                    names.addAll(line.split(" "))
                    return
                }
            }
        }

        names
    }

    String address(String ocr, String name) {
        String address = ocr.replace(name, "---")
        println("------>> " + name)
        println("------>> " + address.replace("\n", " "))

        address = address.split("---")[1]
        println(address)

        address = address.substring(0, address.indexOf("WARGANEGARA"))

        address.replace("\n", " ")
    }

    ArrayList<String> dob(String nric) {
        String ic = nric.replace("-", "")

        String year = ic.take(2)
        String mm = ic.drop(2).take(2)
        String dd = ic.drop(4).take(2)
        String pb = ic.drop(6).take(2)

        String date
        try {
            date = Date.parse("yyMMd", "$year$mm$dd").format("d MMMM, yyyy").toString()
        } catch (ignored) {
            return ['N/A', 'N/A']
        }

        [date, pob(Integer.parseInt(pb))]
    }

    String pob(int n) {
        def pb = ["Johor",
                  "Kedah",
                  "Kelantan",
                  "Malacca",
                  "Negeri Sembilan",
                  "Pahang",
                  "Penang",
                  "Perak",
                  "Perlis",
                  "Selangor",
                  "Terengganu",
                  "Sabah",
                  "Sarawak",
                  "Federal Territory of Kuala Lumpur",
                  "Federal Territory of Labuan",
                  "Federal Territory of Putrajaya",
                  "N/A",
                  "N/A",
                  "N/A",
                  "N/A",
                  "Johor",
                  "Johor",
                  "Johor",
                  "Johor",
                  "Kedah",
                  "Kedah",
                  "Kedah",
                  "Kelantan",
                  "Kelantan",
                  "Malacca",
                  "Negeri Sembilan",
                  "Pahang",
                  "Pahang",
                  "Penang",
                  "Penang",
                  "Perak",
                  "Perak",
                  "Perak",
                  "Perak",
                  "Perlis",
                  "Selangor",
                  "Selangor",
                  "Selangor",
                  "Selangor",
                  "Terengganu",
                  "Terengganu",
                  "Sabah",
                  "Sabah",
                  "Sabah",
                  "Sarawak",
                  "Sarawak",
                  "Sarawak",
                  "Sarawak",
                  "Federal Territory of Kuala Lumpur",
                  "Federal Territory of Kuala Lumpur",
                  "Federal Territory of Kuala Lumpur",
                  "Federal Territory of Kuala Lumpur",
                  "Federal Territory of Labuan",
                  "Negeri Sembilan ",
                  "Brunei",
                  "Indonesia",
                  "Cambodia / Democratic Kampuchea / Kampuchea",
                  "Laos",
                  "Myanmar",
                  "Philippines",
                  "Singapore",
                  "Thailand",
                  "Vietnam",
                  "N/A",
                  "N/A",
                  "A person born outside Malaysia prior to 2001",
                  "A person born outside Malaysia prior to 2001",
                  "N/A",
                  "China",
                  "India",
                  "Pakistan",
                  "Saudi Arabia",
                  "Sri Lanka",
                  "Bangladesh",
                  "N/A",
                  "N/A",
                  "Unknown state",
                  "American Samoa / Asia-Pacific / Australia / Christmas Island / Cocos (Keeling) Islands / Cook Islands / Fiji / French Polynesia / Guam / Heard Island and McDonald Islands / Marshall Islands / Micronesia / New Caledonia / New Zealand / Niue / Norfolk Island / Papua New Guinea / Timor Leste / Tokelau / United States Minor Outlying Islands / Wallis and Futuna Islands",
                  "Anguilla / Argentina / Aruba / Bolivia / Brazil / Chile / Colombia / Ecuador / French Guinea / Guadeloupe / Guyana / Paraguay / Peru / South America / South Georgia and the South Sandwich Islands / Suriname / Uruguay / Venezuela",
                  "Africa / Algeria / Angola / Botswana / Burundi / Cameroon / Central African Republic / Chad / Congo-Brazzaville / Congo-Kinshasa / Djibouti / Egypt / Eritrea / Ethiopia / Gabon / Gambia / Ghana / Guinea / Kenya / Liberia / Malawi / Mali / Mauritania / Mayotte / Morocco / Mozambique / Namibia / Niger / Nigeria / Rwanda / Réunion / Senegal / Sierra Leone / Somalia / South Africa / Sudan / Swaziland / Tanzania / Togo / Tonga / Tunisia / Uganda / Western Sahara / Zaire / Zambia / Zimbabwe",
                  "Armenia / Austria / Belgium / Cyprus / Denmark / Europe / Faroe Islands / France / Finland / Finland, Metropolitan / Germany / Germany, Democratic Republic / Germany, Federal Republic / Greece / Holy See (Vatican City) / Italy / Luxembourg / Macedonia / Malta / Mediterranean / Monaco / Netherlands / Norway / Portugal / Republic of Moldova / Slovakia / Slovenia / Spain / Sweden / Switzerland / United Kingdom-Dependent Territories / United Kingdom-National Overseas / United Kingdom-Overseas Citizen / United Kingdom-Protected Person / United Kingdom-Subject",
                  "Britain / Great Britain / Ireland",
                  "Bahrain / Iran / Iraq / Palestine / Jordan / Kuwait / Lebanon / Middle East / Oman / Qatar / Republic of Yemen / Syria / Turkey / United Arab Emirates / Yemen Arab Republic / Yemen People's Democratic Republic",
                  "Far East / Japan / North Korea / South Korea / Taiwan",
                  "Bahamas / Barbados / Belize / Caribbean / Costa Rica / Cuba / Dominica / Dominican Republic / El Salvador / Grenada / Guatemala / Haiti / Honduras / Jamaica / Martinique / Mexico / Nicaragua / Panama / Puerto Rico / Saint Kitts and Nevis / Saint Lucia / Saint Vincent and the Grenadines / Trinidad and Tobago / Turks and Caicos Islands / Virgin Islands (USA)",
                  "Canada / Greenland / Netherlands Antilles / North America / Saint Pierre and Miquelon / United States of America",
                  "Albania / Belarus / Bosnia and Herzegovina / Bulgaria / Byelorussia / Croatia / Czech Republic / Czechoslovakia / Estonia / Georgia / Hungary / Latvia / Lithuania / Montenegro / Poland / Republic of Kosovo / Romania / Russian Federation / Serbia / Soviet Union / U.S.S.R. / Ukraine",
                  "Afghanistan / Andorra / Antarctica / Antigua and Barbuda / Azerbaijan / Benin / Bermuda / Bhutan / Bora Bora / Bouvet Island / British Indian Ocean Territory / Burkina Faso / Cape Verde / Cayman Islands / Comoros / Dahomey / Equatorial Guinea / Falkland Islands / French Southern Territories / Gibraltar / Guinea-Bissau / Hong Kong / Iceland / Ivory Coast / Kazakhstan / Kiribati / Kyrgyzstan / Lesotho / Libya / Liechtenstein / Macau / Madagascar / Maghribi / Malagasy / Maldives / Mauritius / Mongolia / Montserrat / Nauru / Nepal / Northern Marianas Islands / Outer Mongolia / Palau / Palestine / Pitcairn Islands / Saint Helena / Saint Lucia / Saint Vincent and the Grenadines / Samoa / San Marino / São Tomé and Príncipe / Seychelles / Solomon Islands / Svalbard and Jan Mayen Islands / Tajikistan / Turkmenistan / Tuvalu / Upper Volta / Uzbekistan / Vanuatu / Vatican City / Virgin Islands (British) / Western Samoa / Yugoslavia",
                  "N/A",
                  "N/A",
                  "N/A",
                  "N/A",
                  "Stateless / Stateless Person Article 1/1954",
                  "Mecca / Neutral Zone / No Information / Refugee / Refugee Article 1/1951 / United Nations Specialized Agency / United Nations Organization / Unspecified Nationality ",]

        pb[n]
    }

    String pdfToFile(def stuff) {

        String pdfLocation = "/model/" + stuff.getOriginalFilename().replace(" ", "")

        File pdf = new File(pdfLocation);
        stuff.transferTo(pdf);

        String newNameTemplate = stuff.getOriginalFilename().replace(".pdf", "-pdf").replace(" ", "")

        //xtract PDF
        def sout = new StringBuilder(), serr = new StringBuilder()
        def proc = "python2.7 /model/pdf2jpg.py ${pdfLocation} /model/${newNameTemplate}".execute()
        proc.consumeProcessOutput(sout, serr)
        proc.waitFor()
        println "out> $sout err> $serr"

        String file = sout.toString().trim().replace("/model/", "")


        file
    }

    String ocr(String file, String path) {
        StringBuilder pred = new StringBuilder()
        String filepath = "/model/"

        try {
            String cmd = "python2.7 ${path}r.pyc $filepath${file} checkpoint/model.ckpt-92900.data-00000-of-00001"
            def proc = cmd.execute()
            proc.waitFor()
            def ypred = proc.in.getText('UTF-8').split("\n")
            ypred[0] = null

            for (String line : ypred) {
                pred.append(line)
                pred.append("\n")
            }

            println(pred)

            return pred.toString()

        } catch (Exception e) {
            throw new Exception(e)
        }
    }
}
