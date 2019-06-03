# Import the necessary libraries
import cv2
import argparse

parser = argparse.ArgumentParser()
parser.add_argument('image', help='NRIC image')
parser.add_argument('name', help='Name to save face')
args = parser.parse_args()

#  Loading the image to be tested
test_image = cv2.imread(args.image)

# Converting to grayscale as opencv expects detector takes in input gray scale images
test_image_gray = cv2.cvtColor(test_image, cv2.COLOR_BGR2GRAY)


def convertToRGB(image):
    return cv2.cvtColor(image, cv2.COLOR_BGR2RGB)


haar_cascade_face = cv2.CascadeClassifier('/model/haarcascade_frontalface_alt2.xml')
faces_rects = haar_cascade_face.detectMultiScale(test_image_gray, scaleFactor=1.2, minNeighbors=5);

# Let us print the no. of faces found
print(len(faces_rects))
for (x, y, w, h) in faces_rects:
    cv2.rectangle(test_image, (x, y), (x + w, y + h), (0, 255, 0), 2)

id = 1
for (x, y, w, h) in faces_rects:
    cropped = test_image[y: y + h, x: x + w]
    cv2.imwrite(args.name + ".png", cropped)
    id += 1
