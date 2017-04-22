#version 330 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec3 normal;
layout (location = 2) in vec2 uv;

layout (std140) uniform global {
    float time;
    mat4 projectionView;
};

uniform mat4 model;
uniform mat3 normalMatrix;

out VertexData {
    vec3 position;
    vec3 normal;
    vec2 uv;
} fs;

void main() {
    gl_Position =  projectionView * model * vec4(position, 1.0f);

    // All the lighting calculations are done in world space coordinates.
    fs.position = vec3(model * vec4(position, 1.0f));
    fs.normal = normalMatrix * normal;
    fs.uv = uv;
}

