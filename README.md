# Team SSO Robot Challenge

# TODO 
- Git hub push Unity project
- Clean up Readme
- Put google font back in index html

## Unity Code
To see the code generated for the robot view, see:
https://github.com/TechspiredMinds/robot-challenge-view-unity

The coding part is found under: Assets/scripts/


# What I would do further

- More robust handling of text input (Frontend + Backend)
- More flexible fitting to resolution
- Refactoring of position and robot service -> Seperate classes
- Put Default text into txt file

## CHECK BEFORE SUBMIT

Is robot moving without sending the command first?

## MUST

## SHOULD

- FIX Unity Loading video
- Refactor Position into separate unit

## NICE TO HAVE

- Fix Unity shader so it displays shadows but still one color
- ```Shader "Custom/Unlit Shadowed" {
    Properties {
        _Color ("Color", Color) = (1, 1, 1, 1)
    }
    SubShader {
        Tags { "RenderType"="Opaque" }
        LOD 100

        Pass {
            CGPROGRAM
            #pragma vertex vert
            #pragma fragment frag
            #include "UnityCG.cginc"

            struct appdata {
                float4 vertex : POSITION;
                float3 normal : NORMAL;
            };

            struct v2f {
                float4 vertex : SV_POSITION;
                float3 worldNormal : NORMAL;
                float3 worldPos : TEXCOORD0;
            };

            fixed4 _Color;

            v2f vert (appdata v) {
                v2f o;
                o.vertex = UnityObjectToClipPos(v.vertex);
                o.worldNormal = UnityObjectToWorldNormal(v.normal);
                o.worldPos = mul(unity_ObjectToWorld, v.vertex).xyz;
                return o;
            }

            fixed4 frag (v2f i) : SV_Target {
                return _Color;
            }
            ENDCG
        }
        // Draw shadows
        CGPROGRAM
        #pragma surface surf Lambert
        #pragma target 3.0

        struct ShadowInput {
            float3 worldPos : TEXCOORD0;
        };

        void surf (Input IN, inout SurfaceOutput o) {
            SHADOW_CASTER_SETUP();
            o.Albedo = 1;
            o.Alpha = 1;
            o.Emission = 0;
            o.Normal = float3(0, 1, 0);
        }
        ENDCG
    }
    FallBack "Diffuse"

}```

- Fix unity loading background
- Highlight each command as it is executed
- Sound (Star Wars + Music)
- Quick send button command (TURNAROUND, etc.)