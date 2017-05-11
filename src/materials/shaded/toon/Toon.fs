#version 330 core

struct Material {
    vec4 ambientColor;
    vec4 diffuseColor;
    vec4 specularColor;
    float specularHighlight;

    bool hasDiffuseTexture;
    sampler2D diffuseTexture;
};

struct Global {
    float time;
    mat4 projectionView;
    vec3 cameraPosition;
};

in VertexData {
    vec3 position;
    vec3 normal;
    vec2 uv;
} fs;

uniform Global global;
uniform Material material;

vec4 diffuseTexture();

vec4 ambientComponent(vec4 lightColor) {
    return lightColor * material.ambientColor * diffuseTexture();
}

vec4 diffuseComponent(vec4 lightColor, vec3 lightDirection) {
    float diffuseCoefficient = dot(fs.normal, lightDirection);
    if (diffuseCoefficient > 0.2f) { diffuseCoefficient = 0.7f; }
    else { diffuseCoefficient = 0.2f; }

    return lightColor * material.diffuseColor * diffuseCoefficient
         * diffuseTexture();
}

vec4 specularComponent(vec4 lightColor, vec3 lightDirection) {
    vec3 viewDirection = normalize(global.cameraPosition - fs.position);
    vec3 reflectedDirection = reflect(-lightDirection, fs.normal);

    float specularCoefficient = pow(
        max(dot(viewDirection, reflectedDirection), 0.2f),
        material.specularHighlight
    );
    if (specularCoefficient < 0.5f) { specularCoefficient = 0.0f; }
    else { specularCoefficient = 1.0f; }

    return lightColor * material.specularColor * specularCoefficient;
}
