#version 330 core

struct Node {
    mat4 model;
    mat3 normalMatrix;
};

struct Material {
    samplerCube backTexture;
    samplerCube frontTexture;
};

in VertexData {
    vec3 position;
} fs;

uniform Node node;
uniform Material material;

out vec4 color;

void main() {
    vec4 backColor = texture(
        material.backTexture,
        fs.position
    );

    vec4 frontColor = texture(
        material.frontTexture,
        vec3(node.model * vec4(fs.position, 1.0))
    );

    color = vec4(1.0f, 0.0f, 0.0f, 1.0f);

    color = (frontColor.a < 0.2)
          ? backColor
          : frontColor * 0.5 + backColor * 0.5;
}
