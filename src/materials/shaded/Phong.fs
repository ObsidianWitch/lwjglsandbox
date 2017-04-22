#version 330 core

struct Material {
    vec4 ambientColor;
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

vec4 ambientComponent(vec4 lightColor) {
    vec4 color = lightColor * material.ambientColor;

    if (material.hasDiffuseTexture) {
        color *= texture(material.diffuseTexture, fs.uv);
    }

    return color;
}

