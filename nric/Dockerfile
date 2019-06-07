FROM styl3r/vanilla:latest

LABEL version="1.0"
LABEL description="Install any package using SDKMan"



RUN ["mkdir", "-p", "/apps/home"] 
RUN ["mkdir", "-p", "/model"]
RUN ls -la /apps/home
COPY . /apps/home
WORKDIR /apps/home
RUN apt-get update && apt-get install -y python-argparse libglib2.0-0 poppler-utils
RUN pip2 install -r requirements.txt
RUN apt-get update && apt-get install -y software-properties-common
RUN add-apt-repository -y ppa:alex-p/tesseract-ocr
RUN apt-get update && apt-get install -y tesseract-ocr

EXPOSE 8080
ENTRYPOINT ["./docker-entry.sh"]


