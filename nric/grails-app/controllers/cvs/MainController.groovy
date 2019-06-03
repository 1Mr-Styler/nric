package cvs

class MainController {

    MainService mainService

    def index() {

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
        //~~~~~~~~~~~~~~ Extract NRIC ~~~~~~~~~~~~~
        String nricTemplate = new File("/model/nric.py").getText('UTF-8')

        String nt = nricTemplate.replace("--text--", ocr)
        File nricpy = new File("/tmp/nric.py")
        nricpy.text = nt

        def nircExtract = "/usr/bin/python2.7 /tmp/nric.py".execute()
        nircExtract.waitFor()
        String nric = nircExtract.text
        flash.nric = nric
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        flash.names = mainService.names(ocr, nric)
        def ic = mainService.dob(nric)
        flash.dob = ic[0]
        flash.pob = ic[1]

        if (ocr.toLowerCase().contains("lelaki")) {
            flash.gender = "Male"
        } else if (ocr.toLowerCase().contains("wanita")) {
            flash.gender = "Female"
        } else flash.gender = "N/A"

        chain action: "index"
    }
}
