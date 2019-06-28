package cvs

import grails.converters.JSON


class PassportService {

    def process(String ocr) {
        //~~~~~~~~~~~~~~ Extract Address ~~~~~~~~~~~~~
        String passportTemplate = new File("/model/passport.py").getText('UTF-8')

        String nt = passportTemplate.replace("--text--", ocr)
        File passportpy = new File("/tmp/passport.py")
        passportpy.text = nt

        def passportExtract = "/usr/bin/python2.7 /tmp/passport.py".execute()
        passportExtract.waitFor()
        String passport = passportExtract.text

        JSON.parse(passport)
    }

    String detect(String ocr) {
        def matcher = ocr =~ /(A[0-9]{8})/

        if(matcher.find()){
            matcher[0][0]
        } else null
    }
}
