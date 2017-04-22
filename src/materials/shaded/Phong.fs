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

vec4 diffuseComponent(vec4 lightColor, vec3 lightDirection) {
    float diffuseCoefficient = max(
        dot(fs.normal, lightDirection),
        0.2f
    );

    vec4 color = lightColor * material.diffuseColor * diffuseCoefficient;

    if (material.hasDiffuseTexture) {
        color *= texture(material.diffuseTexture, fs.uv);
    }

    return color;
}

vec4 specularComponent(vec4 lightColor, vec3 lightDirection) {
    // TODO
    return vec4(0.0f);
}
