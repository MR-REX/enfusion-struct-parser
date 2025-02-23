# Enfusion Struct Parser

A Java library for parsing and generating .layer, .ent, and other file formats used by the Enfusion engine (Arma Reforger).
This library is designed to help developers work with game assets, maps, and entity files efficiently.

> [!IMPORTANT]
> Please keep in mind that parsing errors may occur in complex or non-standard file structures, since there is no official or
> complete specification for some file formats (e.g., `.layer`, `.ent`). These files may contain custom or undocumented
> data structures that are difficult to interpret accurately.

## Features

- **Parse and Generate `.layer` files.** Read and write layer files used for map data in Arma Reforger.
- **Parse and Generate `.ent` files.** Work with entity template files to manage game objects and their properties.
- **Primitive Data Types.** Full support for Enfusion-specific primitive types such as `EnfusionBool`, `EnfusionInt`,
`EnfusionFloat`, and `EnfusionString`, ensuring accurate representation and manipulation of game data.
- **Advanced Data Types.** Built-in support for advanced types like `EnfusionVector` and `EnfusionArray`, enabling
seamless handling of complex data structures.
- **Entity Template Parsing.** Parse and manipulate properties, components, groups, and object hierarchies directly from
entity template files.
- **Extensible architecture.** Easily add support for additional file formats used by the Enfusion engine with `EntityTemplateParser`.
- **Easy-to-Use API.** Designed with simplicity and developer productivity in mind.

## Usage

### Parsing Entity Layer from a file:

```java
public class App {
    public static void main(String[] args) {
        try {
            Path layerFilePath = Paths.get("default.layer");
            EntityLayer entityLayer = EntityLayerParser.parseFile(layerFilePath);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
```

### Reading an Entity Layer from one file and writing its structure to another file:

```java
public class App {
    public static void main(String[] args) throws Exception {
        Path fromFilePath = Paths.get("default.layer");
        EntityLayer entityLayer = EntityLayerParser.parseFile(fromFilePath);

        Path toFilePath = Paths.get("generated.layer");

        try (EnfusionObjectWriter writer = new EnfusionObjectWriter(toFilePath)) {
            writer.write(entityLayer);
        }
    }
}
```

## License

This project is licensed under the **MIT License**.

## Acknowledgments

I would like to sincerely thank the **developers of Arma Reforger** and **Bohemia Interactive** for creating such an amazing game
and providing a powerful set of tools for developers - your work is really inspiring.

## Support the Project

If you find this project useful, consider giving it a ⭐ on GitHub!