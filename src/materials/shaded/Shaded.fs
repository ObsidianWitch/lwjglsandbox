#version 330 core

out vec4 color;

vec4 illumination();

void main() {
    color = illumination();
}
