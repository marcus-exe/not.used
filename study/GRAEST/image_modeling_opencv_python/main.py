import cv2 as cv
import numpy as np

# path
inverted_img = cv.imread("./images/kaue.jpeg")

# resize
old_height, old_width = inverted_img.shape[:2]
new_height = int(old_height * 0.6)
new_width = int(old_width * 0.6)
resized_img = cv.resize(inverted_img, (new_width, new_height))

# rotate
transformation_matrix = np.array([[-1, 0, new_width],
                                  [0, -1, new_height],
                                  [0, 0, 1]], dtype=np.float32)
not_inverted_img = cv.warpPerspective(resized_img, transformation_matrix, (new_width, new_height))

# purple filter
final_img = np.zeros_like(not_inverted_img)
final_img[:, :, 0] = not_inverted_img[:, :, 0]  # Keep the blue channel as it is
final_img[:, :, 1] = 0               # Set the green channel to 0 (remove green)
final_img[:, :, 2] = not_inverted_img[:, :, 2]  # Keep the red channel as it is

# Show and Save
cv.imshow('Final Image', final_img)
cv.imwrite('./output/image.jpg', final_img)

cv.waitKey(0)
cv.destroyAllWindows()