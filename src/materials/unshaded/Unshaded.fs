#version 330 core

struct Material {
    bool hasDiffuseColor;
    vec4 diffuseColor;

    bool hasDiffuseTexture;
    sampler2D diffuseTexture;
};

in vec2 fsTextureCoordinates;

out vec4 outColor;

layout (std140) uniform global {
    float time;
};

uniform Material material;

void main() {
    vec4 color = vec4(1.0f);

    if (material.hasDiffuseColor) {
        color *= (0.5f + 0.5 * sin(time)) * material.diffuseColor;
    }

    if (material.hasDiffuseTexture) {
        color *= texture(material.diffuseTexture, fsTextureCoordinates);
    }

    outColor = color;
}

