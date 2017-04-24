#version 330 core

struct Material {
    vec4 ambientColor;
    vec4 diffuseColor;
    vec4 specularColor;
    float specularHighlight;

    bool hasDiffuseTexture;
    sampler2D diffuseTexture;
};

layout (std140) uniform global {
    float time;
    mat4 projectionView;
    vec3 cameraPosition;
};

in VertexData {
    vec3 position;
    vec3 normal;
    vec2 uv;
} fs;

uniform Material material;

vec4 diffuseTexture();

vec4 ambientComponent(vec4 lightColor) {
    return lightColor * material.ambientColor * diffuseTexture();
}

vec4 diffuseComponent(vec4 lightColor, vec3 lightDirection) {
    float diffuseCoefficient = max(
        dot(fs.normal, lightDirection),
        0.2f
    );

    return lightColor * material.diffuseColor * diffuseCoefficient
         * diffuseTexture();
}

vec4 specularComponent(vec4 lightColor, vec3 lightDirection) {
    vec3 viewDirection = normalize(cameraPosition - fs.position);
    vec3 reflectedDirection = reflect(-lightDirection, fs.normal);

    float specularCoefficient = pow(
        max(dot(viewDirection, reflectedDirection), 0.2),
        material.specularHighlight
    );
    return lightColor * material.specularColor * specularCoefficient;
}
