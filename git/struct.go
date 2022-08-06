package main

import (
	"bytes"
	"fmt"
	"io/ioutil"
	"log"
	"strings"
)

type EntryList struct {
	List []Entry
}

type Entry struct {
	Mode string // 10644
	Sha1 string
	Num  int
	Path string
	Type string
}

// type TreeObject struct {
// 	List []Entry
// 	sha1 string
// }

// 重构代码 2022.7.3

type Object interface {
	getContent() []byte
	getType() string
}

type BlobObject struct {
	Path string
	Sha1 string
}

func (blob *BlobObject) getContent() []byte {
	file, err := ioutil.ReadFile(blob.Path)
	if err != nil {
		log.Printf("ERROR:未找到该文件-%s", blob.Path)
		log.Fatal(err)
	}
	return file
}

func (blob *BlobObject) getType() string {
	return "blob"
}

type TreeObject struct {
	List []Entry
	Sha1 string
}

func (tree *TreeObject) getContent() []byte {
	var bytes bytes.Buffer
	for _, entry := range tree.List {
		bytes.WriteString(fmt.Sprintf("%s %s %s %s\n", entry.Mode, entry.Type, entry.Sha1, entry.Path))
	}
	return bytes.Bytes()
}

func (tree *TreeObject) getType() string {
	return "tree"
}

type CommitObject struct {
	Sha1        string // 生成的commit object 对应的sha256值
	parent      string
	message     string
	treeObjSha1 string
	author      string
	committer   string
	date        string
}

func (commit *CommitObject) getContent() []byte {
	var bytes bytes.Buffer
	bytes.WriteString(fmt.Sprintf("tree %s\n", commit.treeObjSha1))
	// commit的parent有可能未指定
	if commit.parent != "" {
		bytes.WriteString(fmt.Sprintf("parent %s\n", commit.parent))
	}
	bytes.WriteString(fmt.Sprintf("author %s %s\n", commit.author, commit.date))
	bytes.WriteString(fmt.Sprintf("committer %s %s\n", commit.committer, commit.date))
	bytes.WriteString(fmt.Sprintf("\n"))
	bytes.WriteString(fmt.Sprintf("%s\n", commit.message))
	return bytes.Bytes()
}

func (commit *CommitObject) getType() string {
	return "commit"
}

func (commit *CommitObject) parseCommitObj(b []byte) *CommitObject {
	buf := bytes.NewBuffer(b)
	line1, err := buf.ReadString('\n')
	if err != nil {
		log.Fatal(err)
	}
	commit.treeObjSha1 = line1[5 : len(line1)-2]

	line2, err := buf.ReadString('\n')
	if err != nil {
		log.Fatal(err)
	}
	// parent不一定指明
	if strings.HasPrefix(line2, "parent") {
		commit.parent = line2[7 : len(line2)-2]

		line3, err := buf.ReadString('\n')
		if err != nil {
			log.Fatal(err)
		}
		s := strings.Split(line3, " ")
		commit.author = s[1]
		var dateStr string
		for i, sub := range s {
			if i > 1 {
				dateStr += sub
			}
		}
		commit.date = dateStr
	} else {
		s := strings.Split(line2, " ")
		commit.author = s[1]
		var dateStr string
		for i, sub := range s {
			if i > 1 {
				dateStr += sub
			}
		}
		commit.date = dateStr
	}
	line4, err := buf.ReadString('\n')
	if err != nil {
		log.Fatal(err)
	}
	s := strings.Split(line4, " ")
	commit.committer = s[1]

	// 中间这一空行（需要忽略）
	_, err = buf.ReadString('\n')
	if err != nil {
		log.Fatal(err)
	}

	line6, err := buf.ReadString('\n')
	if err != nil {
		log.Fatal(err)
	}
	commit.message = line6
	return commit
}
