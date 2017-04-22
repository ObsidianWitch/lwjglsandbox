#version 330 core

struct AmbientLight {
    vec4 color;
};

layout (std140) uniform lights {
    AmbientLight aL;
};

in VertexData {
    vec3 position;
    vec3 normal;
    vec2 uv;
} fs;


vec4 ambientComponent(vec4 color);

vec4 computeAmbientLight(AmbientLight light) {
    return ambientComponent(light.color);
}

vec4 illumination() {
    return computeAmbientLight(aL);
}
