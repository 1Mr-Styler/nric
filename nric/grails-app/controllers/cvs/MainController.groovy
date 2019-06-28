package cvs

class MainController {

    MainService mainService
    PassportService passportService

    def index() {

        if(flash.isp)
            render view: '/passport'
        else
            render view: '/index'
    }

    def upload() {
        if (params.check == null) {
            redirect action: 'index'
            return
        }
        File chequeFile

        chequeFile = mainService.toFile(request.getFile("check"))

        String chequeFilename = chequeFile.name
        String wdir = mainService.processFile(chequeFilename)

        flash.hasUpload = true
        flash.image = "$wdir"

        //Run OCR
        String ocr = mainService.ocr(chequeFilename)
        println(ocr)

        if (passportService.detect(ocr) != null) {
            flash.data = passportService.process(ocr)
            flash.num = passportService.detect(ocr)
            flash.isp = true

        } else {
            //~~~~~~~~~~~~~~ Extract NRIC ~~~~~~~~~~~~~
            String nricTemplate = new File("/model/nric.py").getText('UTF-8')

            String nt = nricTemplate.replace("--text--", ocr)
            File nricpy = new File("/tmp/nric.py")
            nricpy.text = nt

            def nircExtract = "/usr/bin/python2.7 /tmp/nric.py".execute()
            nircExtract.waitFor()
            String nric = nircExtract.text
            def spl = nric.split("\n")
            if (spl.size() > 1)
                nric = spl[0]
            flash.nric = nric
            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

            flash.names = mainService.names(ocr, nric)
            def ic = mainService.dob(nric)
            flash.dob = ic[0]
            flash.pob = ic[1]
            flash.address = mainService.address(ocr, "${flash.names[1]} ${flash.names[2]}")

            if (ocr.toLowerCase().contains("lelaki")) {
                flash.gender = "Male"
            } else if (ocr.toLowerCase().contains("perempuan")) {
                flash.gender = "Female"
            } else flash.gender = "N/A"
        }

        chain action: "index"
    }
}
