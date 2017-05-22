#version 330 core

layout (location = 0) in vec3 position;

struct Global {
    float time;
    mat4 projection;
    mat4 viewUntranslated;
    mat4 projectionView;
    vec3 cameraPosition;
};

uniform Global global;

out VertexData {
    vec3 position;
} fs;

void main() {
    // Sets the position in clip space, and sets the z component to the maximum
    // depth (1.0).
    gl_Position = (
        global.projection * global.viewUntranslated * vec4(position, 1.0f)
    ).xyww;

    fs.position = position;
}
