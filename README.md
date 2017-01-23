# JavaGameEngine

Needed features:
	DataLoader - parses xml data, specifically for strings. This is for loading asset paths (so like the path to an image or sound file or whatever else we may need). We want to be able to search for the asset by some id name. THIS SHOULD BE GENERIC
		You can pick the format for the file, I don't really care. Here's a template
		<resources>
			<asset id='TreeImage'>assets/tree.png</asset>
		</resources>
	AssetHandler - loads an xml file of all the resources and their related ids. Try to make it so that we can easily add resources to the roster of possible resources. Have a method that loads a list of asset ids; a method that clears all the loaded assets; and a method that takes a list of asset ids that keeps assets that are already loaded, releases assets that are not in the list, and loads any resources it needs to. This should be generic, so like it should be able to load/store images, sound files, etc.
	The entire physics engine :) I'll (Spykill) do it soon since I have experience with this. Hopefully it's not too difficult, since I don't really remember too well how I did it