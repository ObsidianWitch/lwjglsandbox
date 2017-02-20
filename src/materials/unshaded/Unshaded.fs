#version 330 core

out vec4 outColor;

uniform bool hasDiffuseColor;
uniform vec4 diffuseColor;

void main() {
    vec4 color = vec4(1.0f);

    if (hasDiffuseColor) { color *= diffuseColor; }

    outColor = color;
}

