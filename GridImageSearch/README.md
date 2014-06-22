androidProjects
===============

Image Search on Flickr

This app searches flick based on a search string and displays the results in grid.
On click of a image on the resulting grid it would display the image in full screen along with the title of the image.

Flickr APIs Used:
// To retrive 18 images for a given search string
https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key={api_key}&tags={search_string}&per_page=18&format=json&nojsoncallback=1

// to retrieve individual images from the String
https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}_[mstzb].jpg
