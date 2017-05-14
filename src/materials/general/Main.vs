#version 330 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec3 normal;
layout (location = 2) in vec2 uv;

struct Global {
    float time;
    mat4 projectionView;
    vec3 cameraPosition;
};

struct Mesh {
    mat4 model;
    mat3 normalMatrix;
};

uniform Global global;
uniform Mesh mesh;

out VertexData {
    vec3 position;
    vec3 normal;
    vec2 uv;
} fs;

void main() {
    gl_Position =  global.projectionView * mesh.model * vec4(position, 1.0f);

    // Lighting calculations are done in world space coordinates.
    fs.position = vec3(mesh.model * vec4(position, 1.0f));
    fs.normal = mesh.normalMatrix * normal;
    fs.uv = uv;
}

