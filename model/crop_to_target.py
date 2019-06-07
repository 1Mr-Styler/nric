import cv2
import argparse
import numpy as np

parser = argparse.ArgumentParser()
parser.add_argument('filename', help='JPG to crop.')
args = parser.parse_args()
img = cv2.imread(args.filename)

gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
th, threshed = cv2.threshold(gray, 240, 255, cv2.THRESH_BINARY_INV)

kernel = cv2.getStructuringElement(cv2.MORPH_ELLIPSE, (11, 11))
morphed = cv2.morphologyEx(threshed, cv2.MORPH_CLOSE, kernel)

cnts = cv2.findContours(morphed, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)[-2]
cnt = sorted(cnts, key=cv2.contourArea)[-1]

x, y, w, h = cv2.boundingRect(cnt)
dst = img[y:y + h, x:x + w]
cv2.imwrite(args.filename, dst)
