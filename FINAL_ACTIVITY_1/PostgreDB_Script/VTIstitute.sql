PGDMP                     
    y            VTInstitute    14.0    14.0 7    $           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            %           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            &           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            '           1262    32771    VTInstitute    DATABASE     i   CREATE DATABASE "VTInstitute" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Spanish_Spain.1252';
    DROP DATABASE "VTInstitute";
                postgres    false            ?            1255    41018 ,   enrollmentinsert(character varying, integer) 	   PROCEDURE       CREATE PROCEDURE public.enrollmentinsert(IN id_card character varying, IN id_code integer)
    LANGUAGE plpgsql
    AS $$
DECLARE 
	enrollment_code integer;
	mysubjects subjects;
BEGIN
	INSERT INTO enrollment (code_enrollment, student, course) VALUES (default, id_card, id_code) RETURNING "code_enrollment" INTO enrollment_code;
	
	FOR mysubjects IN (SELECT * FROM subjects WHERE courseid = id_code) LOOP
		INSERT INTO scores (enrollmentid, subjectid, score) VALUES (enrollment_code, mysubjects.code_subject, 0);
	END LOOP;
END;
$$;
 Z   DROP PROCEDURE public.enrollmentinsert(IN id_card character varying, IN id_code integer);
       public          postgres    false            ?            1255    40980 1   ifstudentisenrollment(character varying, integer)    FUNCTION     +  CREATE FUNCTION public.ifstudentisenrollment(id_card character varying, code_course integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
BEGIN
	IF EXISTS (SELECT student FROM enrollment WHERE id_card = student AND code_course = course) THEN
		RETURN TRUE;
	ELSE
		RETURN FALSE;
	END IF;
END;
$$;
 \   DROP FUNCTION public.ifstudentisenrollment(id_card character varying, code_course integer);
       public          postgres    false            ?            1259    33189    scores    TABLE     u   CREATE TABLE public.scores (
    enrollmentid integer NOT NULL,
    subjectid integer NOT NULL,
    score integer
);
    DROP TABLE public.scores;
       public         heap    postgres    false            ?            1255    41017 &   ifstudentisincourse(character varying)    FUNCTION     ?  CREATE FUNCTION public.ifstudentisincourse(id_card character varying) RETURNS SETOF public.scores
    LANGUAGE plpgsql
    AS $$
DECLARE
code_score scores;
BEGIN
	FOR code_score IN SELECT s.*, e.* FROM scores s INNER JOIN enrollment e ON s.enrollmentid = e.code_enrollment 
				       WHERE score < 5 AND e.student = id_card 
				       GROUP BY e.code_enrollment, s.enrollmentid, s.score, s.subjectid LOOP
		RETURN NEXT code_score;
	END LOOP;
END;
$$;
 E   DROP FUNCTION public.ifstudentisincourse(id_card character varying);
       public          postgres    false    220            ?            1259    33129    course    TABLE     j   CREATE TABLE public.course (
    code_course integer NOT NULL,
    name character varying(50) NOT NULL
);
    DROP TABLE public.course;
       public         heap    postgres    false            ?            1259    33128    course_code_course_seq    SEQUENCE     ?   CREATE SEQUENCE public.course_code_course_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.course_code_course_seq;
       public          postgres    false    210            (           0    0    course_code_course_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public.course_code_course_seq OWNED BY public.course.code_course;
          public          postgres    false    209            ?            1259    33142 
   enrollment    TABLE     ?   CREATE TABLE public.enrollment (
    code_enrollment integer NOT NULL,
    student character varying(20) NOT NULL,
    course integer NOT NULL
);
    DROP TABLE public.enrollment;
       public         heap    postgres    false            ?            1259    33140    enrollment_code_enrollment_seq    SEQUENCE     ?   CREATE SEQUENCE public.enrollment_code_enrollment_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 5   DROP SEQUENCE public.enrollment_code_enrollment_seq;
       public          postgres    false    214            )           0    0    enrollment_code_enrollment_seq    SEQUENCE OWNED BY     a   ALTER SEQUENCE public.enrollment_code_enrollment_seq OWNED BY public.enrollment.code_enrollment;
          public          postgres    false    212            ?            1259    33141    enrollment_course_seq    SEQUENCE     ?   CREATE SEQUENCE public.enrollment_course_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public.enrollment_course_seq;
       public          postgres    false    214            *           0    0    enrollment_course_seq    SEQUENCE OWNED BY     O   ALTER SEQUENCE public.enrollment_course_seq OWNED BY public.enrollment.course;
          public          postgres    false    213            ?            1259    33187    scores_enrollmentid_seq    SEQUENCE     ?   CREATE SEQUENCE public.scores_enrollmentid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 .   DROP SEQUENCE public.scores_enrollmentid_seq;
       public          postgres    false    220            +           0    0    scores_enrollmentid_seq    SEQUENCE OWNED BY     S   ALTER SEQUENCE public.scores_enrollmentid_seq OWNED BY public.scores.enrollmentid;
          public          postgres    false    218            ?            1259    33188    scores_subjectid_seq    SEQUENCE     ?   CREATE SEQUENCE public.scores_subjectid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.scores_subjectid_seq;
       public          postgres    false    220            ,           0    0    scores_subjectid_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.scores_subjectid_seq OWNED BY public.scores.subjectid;
          public          postgres    false    219            ?            1259    33135    student    TABLE     ?   CREATE TABLE public.student (
    id_card character varying(20) NOT NULL,
    firstname character varying(50) NOT NULL,
    lastname character varying(50) NOT NULL,
    email character varying(30),
    phone character varying(15)
);
    DROP TABLE public.student;
       public         heap    postgres    false            ?            1259    33175    subjects    TABLE     ?   CREATE TABLE public.subjects (
    code_subject integer NOT NULL,
    name character varying(50) NOT NULL,
    year character varying(10) NOT NULL,
    courseid integer NOT NULL,
    hours character varying(20)
);
    DROP TABLE public.subjects;
       public         heap    postgres    false            ?            1259    33173    subjects_code_subject_seq    SEQUENCE     ?   CREATE SEQUENCE public.subjects_code_subject_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 0   DROP SEQUENCE public.subjects_code_subject_seq;
       public          postgres    false    217            -           0    0    subjects_code_subject_seq    SEQUENCE OWNED BY     W   ALTER SEQUENCE public.subjects_code_subject_seq OWNED BY public.subjects.code_subject;
          public          postgres    false    215            ?            1259    33174    subjects_courseid_seq    SEQUENCE     ?   CREATE SEQUENCE public.subjects_courseid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public.subjects_courseid_seq;
       public          postgres    false    217            .           0    0    subjects_courseid_seq    SEQUENCE OWNED BY     O   ALTER SEQUENCE public.subjects_courseid_seq OWNED BY public.subjects.courseid;
          public          postgres    false    216            u           2604    33132    course code_course    DEFAULT     x   ALTER TABLE ONLY public.course ALTER COLUMN code_course SET DEFAULT nextval('public.course_code_course_seq'::regclass);
 A   ALTER TABLE public.course ALTER COLUMN code_course DROP DEFAULT;
       public          postgres    false    209    210    210            v           2604    33145    enrollment code_enrollment    DEFAULT     ?   ALTER TABLE ONLY public.enrollment ALTER COLUMN code_enrollment SET DEFAULT nextval('public.enrollment_code_enrollment_seq'::regclass);
 I   ALTER TABLE public.enrollment ALTER COLUMN code_enrollment DROP DEFAULT;
       public          postgres    false    212    214    214            w           2604    33146    enrollment course    DEFAULT     v   ALTER TABLE ONLY public.enrollment ALTER COLUMN course SET DEFAULT nextval('public.enrollment_course_seq'::regclass);
 @   ALTER TABLE public.enrollment ALTER COLUMN course DROP DEFAULT;
       public          postgres    false    213    214    214            z           2604    33192    scores enrollmentid    DEFAULT     z   ALTER TABLE ONLY public.scores ALTER COLUMN enrollmentid SET DEFAULT nextval('public.scores_enrollmentid_seq'::regclass);
 B   ALTER TABLE public.scores ALTER COLUMN enrollmentid DROP DEFAULT;
       public          postgres    false    220    218    220            {           2604    33193    scores subjectid    DEFAULT     t   ALTER TABLE ONLY public.scores ALTER COLUMN subjectid SET DEFAULT nextval('public.scores_subjectid_seq'::regclass);
 ?   ALTER TABLE public.scores ALTER COLUMN subjectid DROP DEFAULT;
       public          postgres    false    220    219    220            x           2604    33178    subjects code_subject    DEFAULT     ~   ALTER TABLE ONLY public.subjects ALTER COLUMN code_subject SET DEFAULT nextval('public.subjects_code_subject_seq'::regclass);
 D   ALTER TABLE public.subjects ALTER COLUMN code_subject DROP DEFAULT;
       public          postgres    false    215    217    217            y           2604    33179    subjects courseid    DEFAULT     v   ALTER TABLE ONLY public.subjects ALTER COLUMN courseid SET DEFAULT nextval('public.subjects_courseid_seq'::regclass);
 @   ALTER TABLE public.subjects ALTER COLUMN courseid DROP DEFAULT;
       public          postgres    false    217    216    217                      0    33129    course 
   TABLE DATA           3   COPY public.course (code_course, name) FROM stdin;
    public          postgres    false    210   3C                 0    33142 
   enrollment 
   TABLE DATA           F   COPY public.enrollment (code_enrollment, student, course) FROM stdin;
    public          postgres    false    214   C       !          0    33189    scores 
   TABLE DATA           @   COPY public.scores (enrollmentid, subjectid, score) FROM stdin;
    public          postgres    false    220   ?C                 0    33135    student 
   TABLE DATA           M   COPY public.student (id_card, firstname, lastname, email, phone) FROM stdin;
    public          postgres    false    211   !D                 0    33175    subjects 
   TABLE DATA           M   COPY public.subjects (code_subject, name, year, courseid, hours) FROM stdin;
    public          postgres    false    217   E       /           0    0    course_code_course_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.course_code_course_seq', 13, true);
          public          postgres    false    209            0           0    0    enrollment_code_enrollment_seq    SEQUENCE SET     M   SELECT pg_catalog.setval('public.enrollment_code_enrollment_seq', 18, true);
          public          postgres    false    212            1           0    0    enrollment_course_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.enrollment_course_seq', 1, false);
          public          postgres    false    213            2           0    0    scores_enrollmentid_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('public.scores_enrollmentid_seq', 1, false);
          public          postgres    false    218            3           0    0    scores_subjectid_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.scores_subjectid_seq', 1, false);
          public          postgres    false    219            4           0    0    subjects_code_subject_seq    SEQUENCE SET     H   SELECT pg_catalog.setval('public.subjects_code_subject_seq', 14, true);
          public          postgres    false    215            5           0    0    subjects_courseid_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.subjects_courseid_seq', 1, false);
          public          postgres    false    216            }           2606    33134    course pk_code_course 
   CONSTRAINT     \   ALTER TABLE ONLY public.course
    ADD CONSTRAINT pk_code_course PRIMARY KEY (code_course);
 ?   ALTER TABLE ONLY public.course DROP CONSTRAINT pk_code_course;
       public            postgres    false    210            ?           2606    33148    enrollment pk_code_enrollment 
   CONSTRAINT     h   ALTER TABLE ONLY public.enrollment
    ADD CONSTRAINT pk_code_enrollment PRIMARY KEY (code_enrollment);
 G   ALTER TABLE ONLY public.enrollment DROP CONSTRAINT pk_code_enrollment;
       public            postgres    false    214            ?           2606    33181    subjects pk_code_subject 
   CONSTRAINT     `   ALTER TABLE ONLY public.subjects
    ADD CONSTRAINT pk_code_subject PRIMARY KEY (code_subject);
 B   ALTER TABLE ONLY public.subjects DROP CONSTRAINT pk_code_subject;
       public            postgres    false    217                       2606    33139    student pk_idcard 
   CONSTRAINT     T   ALTER TABLE ONLY public.student
    ADD CONSTRAINT pk_idcard PRIMARY KEY (id_card);
 ;   ALTER TABLE ONLY public.student DROP CONSTRAINT pk_idcard;
       public            postgres    false    211            ?           2606    33195    scores pk_subject_enrollment 
   CONSTRAINT     o   ALTER TABLE ONLY public.scores
    ADD CONSTRAINT pk_subject_enrollment PRIMARY KEY (enrollmentid, subjectid);
 F   ALTER TABLE ONLY public.scores DROP CONSTRAINT pk_subject_enrollment;
       public            postgres    false    220    220            ?           2606    33182    subjects fk_code_course    FK CONSTRAINT     ?   ALTER TABLE ONLY public.subjects
    ADD CONSTRAINT fk_code_course FOREIGN KEY (courseid) REFERENCES public.course(code_course);
 A   ALTER TABLE ONLY public.subjects DROP CONSTRAINT fk_code_course;
       public          postgres    false    210    217    3197            ?           2606    33196    scores fk_code_enrollment    FK CONSTRAINT     ?   ALTER TABLE ONLY public.scores
    ADD CONSTRAINT fk_code_enrollment FOREIGN KEY (enrollmentid) REFERENCES public.enrollment(code_enrollment);
 C   ALTER TABLE ONLY public.scores DROP CONSTRAINT fk_code_enrollment;
       public          postgres    false    220    214    3201            ?           2606    33201    scores fk_code_subject    FK CONSTRAINT     ?   ALTER TABLE ONLY public.scores
    ADD CONSTRAINT fk_code_subject FOREIGN KEY (subjectid) REFERENCES public.subjects(code_subject);
 @   ALTER TABLE ONLY public.scores DROP CONSTRAINT fk_code_subject;
       public          postgres    false    217    220    3203            ?           2606    33154    enrollment fk_course_code    FK CONSTRAINT     ?   ALTER TABLE ONLY public.enrollment
    ADD CONSTRAINT fk_course_code FOREIGN KEY (course) REFERENCES public.course(code_course);
 C   ALTER TABLE ONLY public.enrollment DROP CONSTRAINT fk_course_code;
       public          postgres    false    214    3197    210            ?           2606    33149    enrollment fk_student_idcard    FK CONSTRAINT     ?   ALTER TABLE ONLY public.enrollment
    ADD CONSTRAINT fk_student_idcard FOREIGN KEY (student) REFERENCES public.student(id_card);
 F   ALTER TABLE ONLY public.enrollment DROP CONSTRAINT fk_student_idcard;
       public          postgres    false    214    3199    211               <   x?3??-?)?,?I,I?/?UH,(PHI-K??/?M?+?24?,OM?6?L,?,?????? F??         C   x?3?L60?4?? ҆???\??@?	?????e
3?LOK)?c?zC??Ԣ???????? ?Z      !   ?   x?-ʱ?0?X*?A~l????1?h???????Z???V?7F??m??Y??Y?/?E????         ?   x?mO?j1<?_?/??&??@	4?Z??iM??[C???~Y%o?8Ѓ???X??S׀????-s?%eN??KN?ybY?օgI????K+}???M$
??p??C??n????'??_(?N?>XZr???޷?7^??R???ĩ5+;KԈ#),??Y??_7f??Df?B;ߵ??S?>?'?6??L2.?e$4YW?+??sLi?)"`?"?>~???t?????         ?   x?m??
?0E?3_?/??i?.???Pq?fH??	$???`??{??hh????E^Pc???S???k?Y?p^f?tn?p'?Y??h??j`????/ʓ??+Yvʐ?|H??O?????x?[V~??HL9o?6?U??(??4?G?x丠
E	??+?????u?!???Dz     