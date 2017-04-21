#version 330 core

struct Material {
    vec4 diffuseColor;

    bool hasDiffuseTexture;
    sampler2D diffuseTexture;
};

in VertexData {
    vec3 position;
    vec2 uv;
} fs;


uniform Material material;

out vec4 color;

void main() {
    vec4 tmpColor = material.diffuseColor;

    if (material.hasDiffuseTexture) {
        tmpColor *= texture(material.diffuseTexture, fs.uv);
    }

    color = tmpColor;
}

