## Imports
import cv2 as cv
import numpy as np

## Read an image
original_img = cv.imread("./images/coruja.jpg")

## Define the desired width and height for the resized image
desired_width = 400
desired_height = 400

## Resize the image
resized_image = cv.resize(original_img, (desired_width, desired_height))


## Define the custom filter (R = 191,  G = 154, B = 208)
custom_filter_beta = np.zeros_like(resized_image)
custom_filter_beta[:, :, 0] = 191             # Set the blue channel to 191 
custom_filter_beta[:, :, 1] = 154             # Set the green channel to 154
custom_filter_beta[:, :, 2] = 208             # Set the red channel to 208

## Apply the custom RGB filter to the image
custom_filter_image_beta = cv.addWeighted(resized_image, 0.7, custom_filter_beta, 0.3, 0)

## Define the custom filter (R = 42, G = -82, B = 98)
custom_filter_alfa = np.zeros_like(resized_image)
custom_filter_alfa[:, :, 0] = np.clip(42, 0, 255)  # Clip to ensure the value is within the valid range
custom_filter_alfa[:, :, 1] = np.clip(-82, 0, 255)  # Adjusted green channel
custom_filter_alfa[:, :, 2] = np.clip(98, 0, 255)  # Clip to ensure the value is within the valid range

## Apply the custom RGB filter to the image
custom_filter_image_alfa = cv.addWeighted(resized_image, 0.7, custom_filter_alfa, 0.3, 0)

custom_filter_image_omega = resized_image.copy()

red_channel = custom_filter_image_omega[:, :, 2]
red_channel_modified = np.clip(red_channel + 42, 0, 255)
custom_filter_image_omega[:, :, 2] = red_channel_modified

green_channel = custom_filter_image_omega[:, :, 1]
green_channel_modified = np.clip(green_channel - 82, 0, 255)
custom_filter_image_omega[:, :, 1] = green_channel_modified

blue_channel = custom_filter_image_omega[:, :, 0]
blue_channel_modified = np.clip(blue_channel + 98, 0, 255)
custom_filter_image_omega[:, :, 0] = blue_channel_modified


## Alternative without the green color
purple_filter_image = np.zeros_like(resized_image)
purple_filter_image[:, :, 0] = resized_image[:, :, 0]  # Keep the blue channel as it is
purple_filter_image[:, :, 1] = 0               # Set the green channel to 0 (remove green)
purple_filter_image[:, :, 2] = resized_image[:, :, 2]  # Keep the red channel as it is



## Save Images
# Save the original and filtered images
cv.imwrite('./output/tests/original_image.jpg', resized_image)
cv.imwrite('./output/tests/custom_filtered_image_beta.jpg', custom_filter_image_beta)
cv.imwrite('./output/tests/custom_filtered_image_alfa.jpg', custom_filter_image_alfa)
cv.imwrite('./output/tests/custom_filtered_image_omega.jpg', custom_filter_image_omega)
cv.imwrite('./output/tests/purple_filtered_image.jpg', purple_filter_image)

## Show the images
cv.imshow('Original Image', resized_image)
cv.imshow('Filtered Image Alfa', custom_filter_image_alfa)
cv.imshow('Filtered Image Beta', custom_filter_image_beta)
cv.imshow('Filtered Image Omega', custom_filter_image_omega)
cv.imshow('Purple Image', purple_filter_image)
cv.waitKey(0)
cv.destroyAllWindows()
