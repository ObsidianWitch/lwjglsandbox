#version 330 core

#if defined(SHADED)
struct Material {
    vec4 ambientColor;
    vec4 diffuseColor;
    vec4 specularColor;
    float specularHighlight;

    bool hasDiffuseTexture;
    sampler2D diffuseTexture;
};
#elif defined(UNSHADED)
struct Material {
    vec4 diffuseColor;

    bool hasDiffuseTexture;
    sampler2D diffuseTexture;
};
#endif

in VertexData {
    vec3 position;
    vec3 normal;
    vec2 uv;
} fs;

uniform Material material;

vec4 diffuseTexture() {
    return material.hasDiffuseTexture
         ? texture(material.diffuseTexture, fs.uv)
         : vec4(1.0f);
}
