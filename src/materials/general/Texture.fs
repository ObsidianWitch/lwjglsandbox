#version 330 core

in VertexData {
    vec3 position;
    vec3 normal;
    vec2 uv;
} fs;

vec4 diffuseTexture(bool hasDiffuseTexture, sampler2D diffuseTexture) {
    return hasDiffuseTexture
         ? texture(diffuseTexture, fs.uv)
         : vec4(1.0f);
}
