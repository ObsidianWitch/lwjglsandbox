#version 330 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec3 normal;
layout (location = 2) in vec2 uv;

struct Global {
    float time;
    mat4 projectionView;
    vec3 cameraPosition;
};

struct Node {
    mat4 model;
    mat3 normalMatrix;
};

uniform Global global;
uniform Node node;

out VertexData {
    vec3 position;
    vec3 normal;
    vec2 uv;
} fs;

void main() {
    gl_Position =  global.projectionView * node.model * vec4(position, 1.0f);

    // Lighting calculations are done in world space coordinates.
    fs.position = vec3(node.model * vec4(position, 1.0f));
    fs.normal = node.normalMatrix * normal;
    fs.uv = uv;
}

