import cv2 as cv
import numpy as np

## Read an image
original_img = cv.imread("./images/arte.jpg")

# Get the height and width of the image
original_height, original_width = original_img.shape[:2]

# Resize image

# Calculate the new height and width
new_height = int(original_height * 0.25)
new_width = int(original_width * 0.25)

# Resize the image to 50% of its original size
resized_img = cv.resize(original_img, (new_width, new_height))

# Define the transformation matrix for the convex lens effect

# This matrix will invert the image around its center
transformation_matrix = np.array([[-1, 0, new_width],
                                  [0, -1, new_height],
                                  [0, 0, 1]], dtype=np.float32)

# Apply the perspective transformation to the image
inverted_img = cv.warpPerspective(resized_img, transformation_matrix, (new_width, new_height))
 
not_inverted_img = cv.warpPerspective(inverted_img, transformation_matrix, (new_width, new_height))

# Display the original and inverted images
cv.imshow('Original Image', resized_img)
cv.imshow('Inverted Image (Convex Lens Effect)', inverted_img)
cv.imshow('Not Inverted Image', not_inverted_img)

## Save Images
# Save the original and filtered images
cv.imwrite('./output/tests/original_image.jpg', resized_img)
cv.imwrite('./output/tests/inverted_image.jpg', inverted_img)
cv.imwrite('./output/tests/not_inverted_image.jpg', not_inverted_img)

cv.waitKey(0)
cv.destroyAllWindows()