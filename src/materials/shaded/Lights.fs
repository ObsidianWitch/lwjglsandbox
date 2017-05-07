#version 330 core

struct AmbientLight {
    vec4 color;
};

struct DirectionalLight {
    vec4 color;
    vec3 direction;
};

struct PointLight {
    vec4 color;
    vec3 position;
    float constant;
    float linear;
    float quadratic;
};

in VertexData {
    vec3 position;
    vec3 normal;
    vec2 uv;
} fs;

uniform AmbientLight aL;
uniform DirectionalLight dL;
uniform PointLight pL;

vec4 ambientComponent(vec4 lightColor);
vec4 diffuseComponent(vec4 lightColor, vec3 lightDirection);
vec4 specularComponent(vec4 lightColor, vec3 lightDirection);

vec4 computeAmbientLight(AmbientLight light) {
    return ambientComponent(light.color);
}

vec4 computeDirectionalLight(DirectionalLight light) {
    vec3 lightDirection = normalize(-light.direction);

    return diffuseComponent(light.color, lightDirection)
         + specularComponent(light.color, lightDirection);
}

vec4 computePointLight(PointLight light) {
    vec3 lightDir = light.position - fs.position;
    vec3 lightDirNorm = normalize(lightDir);

    float distance = length(lightDir);
    float attenuation = 1.0f / (
        light.constant
      + light.linear * distance
      + light.quadratic * distance * distance
    );

    return attenuation * (diffuseComponent(light.color, lightDirNorm)
         + specularComponent(light.color, lightDirNorm));
}

vec4 illumination() {
    return computeAmbientLight(aL)
         + computeDirectionalLight(dL)
         + computePointLight(pL);
}
