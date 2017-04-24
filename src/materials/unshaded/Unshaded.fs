#version 330 core

struct Material {
    vec4 diffuseColor;

    bool hasDiffuseTexture;
    sampler2D diffuseTexture;
};

in VertexData {
    vec3 position;
    vec3 normal;
    vec2 uv;
} fs;

uniform Material material;

out vec4 color;

vec4 diffuseTexture();

void main() {
    color = material.diffuseColor * diffuseTexture();
}

